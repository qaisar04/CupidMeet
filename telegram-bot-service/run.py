from aiogram import Dispatcher, Bot
import asyncio
from dotenv import load_dotenv
from aiogram.filters import CommandStart
from aiogram.types import Message
import os

load_dotenv()

TOKEN = os.getenv('TOKEN')
bot = Bot(token=TOKEN)

dp = Dispatcher()


@dp.message(CommandStart())
async def cmd_start(message: Message):
    await message.answer('Привет')


async def main():
    await dp.start_polling(bot)


if __name__ == '__main__':
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print('Exit')
