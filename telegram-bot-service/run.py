from aiogram import Dispatcher, Bot
import asyncio
from dotenv import load_dotenv
import os
from app.api.connection import StorageService, GeoGetService, UserDetailsService
from app.handlers import handlers, register

load_dotenv()

TOKEN = os.getenv('TOKEN')


class CuppidBot(Bot):
    def __init__(self, user_details_service: UserDetailsService, geo_get_service: GeoGetService,
                 storage_service: StorageService, token: str, *args, **kwargs):
        super().__init__(token=token, *args, **kwargs)
        self.user_details_service = user_details_service
        self.geo_get_service = geo_get_service
        self.storage_service = storage_service


user_details_service = UserDetailsService(base_url='http://qaisar.online:8728/')
storage_service = StorageService(base_url='http://qaisar.online/storage-service/')
geo_get_service = GeoGetService(base_url='http://qaisar.online:8580/')
bot = CuppidBot(token=TOKEN, user_details_service=user_details_service, geo_get_service=geo_get_service,
                storage_service=storage_service)


async def main():
    dp = Dispatcher()
    await bot.delete_webhook(drop_pending_updates=True)
    dp.include_routers(handlers.router, register.registration_router)
    await dp.start_polling(bot)


if __name__ == '__main__':
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print('Бот выключен')
