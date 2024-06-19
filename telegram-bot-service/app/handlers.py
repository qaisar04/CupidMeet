from aiogram import Router, types
from aiogram.filters import CommandStart, StateFilter
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import State, StatesGroup
from aiogram.types import Message
from app.fakeData.utils import is_exist, add_user, read_data
from app.keyboards import profile

router = Router()


class RegistrationStates(StatesGroup):
    waiting_for_name = State()
    waiting_for_age = State()
    waiting_for_city = State()
    waiting_for_gender = State()
    waiting_for_personality_type = State()
    waiting_for_bio = State()


@router.message(CommandStart())
async def cmd_start(message: Message, state: FSMContext):
    if not await is_exist(message.from_user.id):
        await message.answer("Привет! Давайте зарегистрируем вас. Как вас зовут?",
                             reply_markup=profile([message.from_user.first_name]))
        await state.set_state(RegistrationStates.waiting_for_name)
    else:
        await message.answer("Вы уже зарегистрированы.")


@router.message(StateFilter(RegistrationStates.waiting_for_name))
async def process_name(message: Message, state: FSMContext):
    await state.update_data(name=message.text)
    await message.answer("Сколько вам лет?")
    await state.set_state(RegistrationStates.waiting_for_age)


@router.message(lambda message: not message.text.isdigit(), StateFilter(RegistrationStates.waiting_for_age))
async def process_age_invalid(message: Message):
    return await message.reply("Возраст должен быть числом. Попробуйте снова:")


@router.message(StateFilter(RegistrationStates.waiting_for_age))
async def process_age(message: Message, state: FSMContext):
    await state.update_data(age=int(message.text))
    await message.answer("В каком городе вы живете?")
    await state.set_state(RegistrationStates.waiting_for_city)


@router.message(StateFilter(RegistrationStates.waiting_for_city))
async def process_city(message: Message, state: FSMContext):
    await state.update_data(city=message.text)
    await message.answer("Ваш пол? (MALE/FEMALE)")
    await state.set_state(RegistrationStates.waiting_for_gender)


@router.message(StateFilter(RegistrationStates.waiting_for_gender))
async def process_gender(message: Message, state: FSMContext):
    await state.update_data(gender=message.text.upper())
    await message.answer("Ваш тип личности по MBTI?")
    await state.set_state(RegistrationStates.waiting_for_personality_type)


@router.message(StateFilter(RegistrationStates.waiting_for_personality_type))
async def process_personality_type(message: Message, state: FSMContext):
    await state.update_data(personalityType=message.text.upper())
    await message.answer("Напишите немного о себе.")
    await state.set_state(RegistrationStates.waiting_for_bio)


@router.message(StateFilter(RegistrationStates.waiting_for_bio))
async def process_bio(message: Message, state: FSMContext):
    await state.update_data(bio=message.text)

    # Получение всех данных пользователя
    user_data = await state.get_data()
    user_data['telegram_id'] = message.from_user.id
    user_data['username'] = message.from_user.username

    # Добавление пользователя в базу данных
    await add_user(user_data)

    await message.answer("Регистрация завершена. Спасибо!")
    await state.clear()

# Регистрация хэндлеров
