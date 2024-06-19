from aiogram import Dispatcher, Bot
import asyncio
from dotenv import load_dotenv
from app.handlers import router
import os

load_dotenv()

TOKEN = os.getenv('TOKEN')


async def main():
    bot = Bot(token=TOKEN)
    dp = Dispatcher()
    await bot.delete_webhook(drop_pending_updates=True)
    dp.include_router(router)
    await dp.start_polling(bot)


if __name__ == '__main__':
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print('Бот выключен')
