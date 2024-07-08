import requests
from aiogram import Router, types, F
from aiogram.filters import CommandStart, StateFilter, Command
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import State, StatesGroup
from aiogram.types import Message
from app.fakeData.utils import is_exist, add_user, read_data, deactivate_user, get_user, update_user
from app.keyboards.keyboards import profile
from app.keyboards.keyboards import menu, location_keyboard, gender_keyboard, mbti_keyboard
from run import bot

router = Router()


@router.message(F.text == 'Моя анкета')
async def edit_profile(message: Message):
    data = await get_user(message.from_user.id)

    await message.answer(
        f"""
       Имя: {data['name']}
        Возраст:{data['age']}
       Тип личности: {data['personalityType']}
       О себе: {data['bio']}
"""
    )


class UpdateStates(StatesGroup):
    waiting_for_name = State()
    waiting_for_age = State()
    waiting_for_city = State()
    waiting_for_gender = State()
    waiting_for_personality_type = State()
    waiting_for_bio = State()
    waiting_for_preference_gender = State()


@router.message(F.text == 'Редактирование')
async def start_edit_profile(message: Message, state: FSMContext):
    data = await get_user(message.from_user.id)
    if data:
        await state.update_data(user_data=data)
        await message.answer("Начнем редактирование вашего профиля. Как вас зовут?")
        await state.set_state(UpdateStates.waiting_for_name)
    else:
        await message.answer("Ваша анкета не найдена.")


@router.message(StateFilter(UpdateStates.waiting_for_name))
async def process_name_update(message: Message, state: FSMContext):
    new_name = message.text
    if new_name.lower() != "пропустить":
        await update_user(message.from_user.id, {"name": new_name})
    await message.answer("Сколько вам лет?")
    await state.set_state(UpdateStates.waiting_for_age)


@router.message(StateFilter(UpdateStates.waiting_for_age))
async def process_age_update(message: Message, state: FSMContext):
    new_age = message.text
    if new_age.lower() != "пропустить":
        await update_user(message.from_user.id, {"age": new_age})
    await message.answer("В каком городе вы живете?", reply_markup=location_keyboard)
    await state.set_state(UpdateStates.waiting_for_city)


@router.message(StateFilter(UpdateStates.waiting_for_city))
async def process_city_update(message: Message, state: FSMContext):
    location = message.location
    if location:
        await update_user(message.from_user.id, {
            "location": {
                "latitude": location.latitude,
                "longitude": location.longitude
            }
        })
    await message.answer("Ваш пол?", reply_markup=gender_keyboard)
    await state.set_state(UpdateStates.waiting_for_gender)


@router.message(StateFilter(UpdateStates.waiting_for_gender))
async def process_gender_update(message: Message, state: FSMContext):
    if message.text == "Парень":
        await update_user(message.from_user.id, {"gender": "MALE"})
    elif message.text == "Девушка":
        await update_user(message.from_user.id, {"gender": "FEMALE"})
    await message.answer("Ваш тип личности по MBTI?", reply_markup=mbti_keyboard)
    await state.set_state(UpdateStates.waiting_for_personality_type)


@router.message(StateFilter(UpdateStates.waiting_for_personality_type))
async def process_personality_type_update(message: Message, state: FSMContext):
    new_personality_type = message.text.upper()
    if new_personality_type.lower() != "пропустить":
        await update_user(message.from_user.id, {"personalityType": new_personality_type})
    await message.answer("Кого ищете?", reply_markup=gender_keyboard)
    await state.set_state(UpdateStates.waiting_for_preference_gender)


@router.message(StateFilter(UpdateStates.waiting_for_preference_gender))
async def process_preference_gender_update(message: Message, state: FSMContext):
    if message.text == "Парень":
        await update_user(message.from_user.id, {"preference_gender": "MALE"})
    elif message.text == "Девушка":
        await update_user(message.from_user.id, {"preference_gender": "FEMALE"})
    await message.answer("Расскажите о себе")
    await state.set_state(UpdateStates.waiting_for_bio)


@router.message(StateFilter(UpdateStates.waiting_for_bio))
async def process_bio_update(message: Message, state: FSMContext):
    new_bio = message.text
    if new_bio.lower() != "пропустить":
        await update_user(message.from_user.id, {"bio": new_bio})
    await message.answer("Ваш профиль успешно обновлен.")
    await state.clear()


class FeedbackStates(StatesGroup):
    waiting_for_feedback = State()


@router.message(F.text == "Обратная связь")
async def request_feedback(message: Message, state: FSMContext):
    await message.answer("Пожалуйста, оставьте ваш отзыв:")
    await state.set_state(FeedbackStates.waiting_for_feedback)


@router.message(StateFilter(FeedbackStates.waiting_for_feedback))
async def process_feedback(message: Message, state: FSMContext):
    feedback_text = message.text
    user_data = await get_user(message.from_user.id)
    feedback_data = {
        "telegram_id": message.from_user.id,
        "username": message.from_user.username,
        "feedback": feedback_text,
        "user_data": user_data
    }
    await message.reply('спасибо за отзыв')
    """
    response = await send_feedback_to_api(feedback_data)
    if response.get("success"):
        await message.answer("Спасибо за ваш отзыв!")
    else:
        await message.answer("Произошла ошибка при отправке отзыва. Пожалуйста, попробуйте позже.")
    await state.clear()
    """


@router.message(Command("menu"))
async def send_welcome(message: types.Message):
    await message.reply("Welcome to the bot! Please choose an option from the menu below:", reply_markup=menu)


@router.message(F.text == "Деактивация")
async def deactivate(message: types.Message):
    await deactivate_user(message.from_user.id)
    await message.reply("Аккаунт декативирован")
