import requests
from aiogram import Router, types, F
from aiogram.filters import CommandStart, StateFilter, Command
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import State, StatesGroup
from aiogram.types import Message
from app.keyboards.keyboards import profile
from app.keyboards.keyboards import menu, location_keyboard, gender_keyboard, mbti_keyboard
import run
from app.middlewares import AlbumMiddleware
registration_router = Router()
registration_router.message.middleware(AlbumMiddleware())

class RegistrationStates(StatesGroup):
    waiting_for_name = State()
    waiting_for_age = State()
    waiting_for_city = State()
    waiting_for_gender = State()
    waiting_for_personality_type = State()
    waiting_for_bio = State()
    waiting_for_preference_gender = State()
    waiting_for_media = State()


@registration_router.message(CommandStart())
async def cmd_start(message: Message, state: FSMContext):
    if run.bot.user_details_service.get_user_details(message.from_user.id).status_code != requests.codes.ok:
        run.bot.user_details_service.create_new_user(
            data={'id': message.from_user.id, 'username': message.from_user.username})
        await message.answer(f"Привет! Давайте зарегистрируем вас. Как вас зовут?",
                             reply_markup=profile([message.from_user.first_name]))
        await state.set_state(RegistrationStates.waiting_for_name)
    else:
        await message.answer("Вы уже зарегистрированы.")


@registration_router.message(StateFilter(RegistrationStates.waiting_for_name))
async def process_name(message: Message, state: FSMContext):
    await state.update_data(name=message.text)
    await message.answer("Сколько вам лет?")
    await state.set_state(RegistrationStates.waiting_for_age)


@registration_router.message(StateFilter(RegistrationStates.waiting_for_age))
async def process_age(message: Message, state: FSMContext):
    await state.update_data(age=int(message.text))
    await message.answer("В каком городе вы живете?", reply_markup=location_keyboard)
    await state.set_state(RegistrationStates.waiting_for_city)


@registration_router.message(StateFilter(RegistrationStates.waiting_for_city))
async def process_city(message: Message, state: FSMContext):
    location = message.location  # Получаем объект с информацией о местоположении
    # loc = bot.user_details_service.get_location_data_by_coordinates(data={'latitude': location.latitude, 'longitude': location.longitude})
    await state.update_data(city='string')
    await message.answer("Ваш пол?", reply_markup=gender_keyboard)
    await state.set_state(RegistrationStates.waiting_for_gender)


@registration_router.message(StateFilter(RegistrationStates.waiting_for_gender))
async def process_gender(message: Message, state: FSMContext):
    if message.text == "Парень":
        await state.update_data(gender="MALE")
    elif message.text == "Девушка":
        await state.update_data(gender="FEMALE")
    await message.answer("Ваш тип личности по MBTI?", reply_markup=mbti_keyboard)
    await state.set_state(RegistrationStates.waiting_for_personality_type)


@registration_router.message(StateFilter(RegistrationStates.waiting_for_personality_type))
async def process_personality_type(message: Message, state: FSMContext):
    await state.update_data(personalityType=message.text.upper())
    await message.answer("Кого ищете?", reply_markup=gender_keyboard)
    await state.set_state(RegistrationStates.waiting_for_preference_gender)


@registration_router.message(StateFilter(RegistrationStates.waiting_for_preference_gender))
async def process_preference_gender(message: Message, state: FSMContext):
    if message.text == "Парень":
        await state.update_data(preference_gender="MALE")
    elif message.text == "Девушка":
        await state.update_data(preference_gender="FEMALE")
    await message.answer("Расскажите о себе")
    await state.set_state(RegistrationStates.waiting_for_bio)


@registration_router.message(StateFilter(RegistrationStates.waiting_for_bio))
async def process_bio(message: Message, state: FSMContext):
    await state.update_data(bio=message.text)
    await message.answer('Отправьте фотографии')
    await state.set_state(RegistrationStates.waiting_for_media)


@registration_router.message(StateFilter(RegistrationStates.waiting_for_media))
async def process_media(message: Message, state: FSMContext, album: list| None):
    if album:
        for media_item in album:
            if media_item.content_type == types.ContentType.PHOTO:
                # Получаем file_id последней (большой) версии фото
                file_id = media_item.photo[-1].file_id
                await message.answer_photo(file_id)

    # Отправляем данные пользователя в сервис пользовательских данных
    user_data = await state.get_data()
    data = {
        'name': user_data.get('name', 'string'),
        'age': user_data.get('age', 150),
        'city': user_data.get('city', 'string'),
        'gender': user_data.get('gender', 'FEMALE'),
        'personalityType': user_data.get('personalityType', 'ISTP'),
        'bio': user_data.get('bio', 'string')
    }
    run.bot.user_details_service.create_user_information(telegram_id=message.from_user.id, data=data)

    # Отправляем сообщение о завершении регистрации
    await message.answer("Регистрация завершена. Спасибо!")

    # Очищаем состояние
    await state.clear()