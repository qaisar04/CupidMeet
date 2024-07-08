from aiogram.types import ReplyKeyboardMarkup, KeyboardButton
from aiogram.utils.keyboard import ReplyKeyboardBuilder

start_bot = ReplyKeyboardMarkup(keyboard=[[KeyboardButton(text='да'), KeyboardButton(text='может быть позже')]])


def profile(text: str | list):
    builder = ReplyKeyboardBuilder()

    if isinstance(text, str):
        text = list(text)

    [builder.button(text=txt) for txt in text]

    return builder.as_markup(resize_keyboard=True, one_time_keyboard=True)


menu = ReplyKeyboardMarkup(keyboard=[[
    KeyboardButton(text='Деактивация'), KeyboardButton(text='Редактирование')
],
    [KeyboardButton(text='Обратная связь'), KeyboardButton(text='Моя анкета')],
    [
        KeyboardButton(text='Смена языка')
    ]
], resize_keyboard=True)

location_keyboard = ReplyKeyboardMarkup(
    keyboard=[[
        KeyboardButton(text="Отправить местоположение", request_location=True)
    ]],
    resize_keyboard=True, one_time_keyboard=True
)


gender_keyboard = ReplyKeyboardMarkup(
    keyboard = [
        [KeyboardButton(text='Девушка'), KeyboardButton(text='Парень')]
    ],
    resize_keyboard=True, one_time_keyboard=True
)

mbti_types = [
    "INTJ", "INTP", "ENTJ", "ENTP",
    "INFJ", "INFP", "ENFJ", "ENFP",
    "ISTJ", "ISFJ", "ESTJ", "ESFJ",
    "ISTP", "ISFP", "ESTP", "ESFP"
]

# Создаем клавиатуру 4x4 с использованием KeyboardButton
mbti_keyboard = ReplyKeyboardMarkup(
    keyboard=[
        [KeyboardButton(text=mbti_types[0]), KeyboardButton(text=mbti_types[1]), KeyboardButton(text=mbti_types[2]), KeyboardButton(text=mbti_types[3])],
        [KeyboardButton(text=mbti_types[4]), KeyboardButton(text=mbti_types[5]), KeyboardButton(text=mbti_types[6]), KeyboardButton(text=mbti_types[7])],
        [KeyboardButton(text=mbti_types[8]), KeyboardButton(text=mbti_types[9]), KeyboardButton(text=mbti_types[10]), KeyboardButton(text=mbti_types[11])],
        [KeyboardButton(text=mbti_types[12]), KeyboardButton(text=mbti_types[13]), KeyboardButton(text=mbti_types[14]), KeyboardButton(text=mbti_types[15])]
    ],
    resize_keyboard=True,
    one_time_keyboard=True
)