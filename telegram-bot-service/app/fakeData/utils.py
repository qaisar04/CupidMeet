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
            if record['is_active'] == 'True':
                return True
            else:
                await activate_user(telegram_id)
                return True
    return False


# Асинхронное добавление нового пользователя
async def add_user(new_record):
    data = await read_data()
    data.append(new_record)
    async with aiofiles.open('db.json', 'w') as file:
        await file.write(json.dumps(data, indent=4))
    return data


async def deactivate_user(telegram_id):
    data = await read_data()
    for record in data:
        if record['telegram_id'] == str(telegram_id):
            if record['is_active'] == 'True':
                record['is_active'] = 'False'
                break

    async with aiofiles.open('db.json', 'w') as file:
        await file.write(json.dumps(data, indent=4))
    return data

async def activate_user(telegram_id):
    data = await read_data()
    for record in data:
        if record['telegram_id'] == str(telegram_id):
            if record['is_active'] == 'False':
                record['is_active'] = 'True'
                break

    async with aiofiles.open('db.json', 'w') as file:
        await file.write(json.dumps(data, indent=4))
    return data

async def get_user(telegram_id):
    data = await read_data()
    for record in data:
        if record['telegram_id'] == telegram_id:
            return record


# Асинхронное обновление данных пользователя
async def update_user(telegram_id, update_data):
    data = await read_data()
    for record in data:
        if record['telegram_id'] == telegram_id:
            for key, value in update_data.items():
                if key in record:
                    record[key] = value
                else:
                    raise ValueError(f"Поле {key} не существует в записи пользователя")
            break
    else:
        raise ValueError("Пользователь с таким telegram_id не найден")

    async with aiofiles.open('db.json', 'w') as file:
        await file.write(json.dumps(data, indent=4))
    return data
