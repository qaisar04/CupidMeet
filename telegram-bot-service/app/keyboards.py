from aiogram.types import ReplyKeyboardMarkup, KeyboardButton
from aiogram.utils.keyboard import ReplyKeyboardBuilder
start_bot = ReplyKeyboardMarkup(keyboard=[[KeyboardButton(text='да'), KeyboardButton(text='может быть позже')]])


def profile(text: str | list):
    builder = ReplyKeyboardBuilder()

    if isinstance(text, str):
        text = list(text)

    [builder.button(text=txt) for txt in text]

    return builder.as_markup(resize_keyboard=True, one_time_keyboard=True)



