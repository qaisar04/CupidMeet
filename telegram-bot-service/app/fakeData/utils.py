import json
import aiofiles  # Используем aiofiles для асинхронной работы с файлами

# Асинхронное чтение JSON из файла
async def read_data():
    async with aiofiles.open('db.json', 'r') as file:
        data = await file.read()
        return json.loads(data)

# Проверка существования пользователя
async def is_exist(telegram_id):
    data = await read_data()
    for record in data:
        if record['telegram_id'] == telegram_id:
            return True
    return False

# Асинхронное добавление нового пользователя
async def add_user(new_record):
    data = await read_data()
    data.append(new_record)
    async with aiofiles.open('db.json', 'w') as file:
        await file.write(json.dumps(data, indent=4))
    return data
