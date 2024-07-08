import requests


url = 'http://65.0.96.229:8728/'


class UserDetailsService:

    def __init__(self, base_url: str):
        self.base_url = base_url

    def create_new_user(self, data):
        endpoint = 'api/v1/user'
        response = requests.post(self.base_url + endpoint, json=data)
        return response

    def deactivate_user(self, telegram_id):
        endpoint = f'api/v1/user/{telegram_id}/deactivate'
        response = requests.patch(self.base_url + endpoint)
        return response

    def get_user_details(self, telegram_id):
        endpoint = f'api/v1/user/{telegram_id}'
        response = requests.get(self.base_url + endpoint)
        return response

    def create_user_preference(self, telegram_id, data):
        endpoint = f'api/v1/preference/{telegram_id}'
        response = requests.post(self.base_url + endpoint, json=data)
        return response

    def update_user_preference(self, telegram_id):
        endpoint = f'api/v1/preference/{telegram_id}'
        response = requests.patch(self.base_url + endpoint)
        return response

    def create_user_information(self, telegram_id, data):
        endpoint = f'api/v1/info/{telegram_id}'
        response = requests.post(self.base_url + endpoint, json=data)
        return response

    def update_user_information(self, telegram_id):
        endpoint = f'api/v1/info/{telegram_id}/update'
        response = requests.post(self.base_url + endpoint)
        return response

    def add_attachments_to_user_information(self, telegram_id):
        endpoint = f'api/v1/info/{telegram_id}/attachments'
        response = requests.post(self.base_url + endpoint)
        return response

    def remove_attachent(self, telegram_id):
        endpoint = f'api/v1/info/attachments/{telegram_id}'
        response = requests.delete(self.base_url + endpoint)
        return response

    def activate_user(self, telegram_id):
        response = self.get_user_details(telegram_id)
        if response.json()['status'] == 'INACTIVE':
            endpoint = f'api/v1/{telegram_id}/activate'
            response = requests.patch(self.base_url + endpoint)
            return response


class AttachmentService:

    def __init__(self, base_url):
        self.base_url = base_url

    def upload_file(self):
        endpoint = 'api/v1/file/upload'
        response = requests.post(self.base_url + endpoint)
        return response.json()

    def upload_multiple_files(self):
        endpoint = 'api/v1/file/upload/batch'
        response = requests.post(self.base_url + endpoint)
        return response.json()

    def download_file_by_path(self):
        endpoint = 'api/v1/file'
        response = requests.get(self.base_url + endpoint)
        return response.json()

    def delete_multiple_files_by_ids(self):
        endpoint = 'api/v1/file'
        response = requests.delete(self.base_url + endpoint)
        return response.json()

    def delete_file_by_id(self, file_id):
        endpoint = f'api/v1/file/{file_id}'
        response = requests.delete(self.base_url + endpoint)
        return response.json()


class GeoGetService:

    def __init__(self, base_url):
        self.base_url = base_url

    def get_location_data_by_coordinates(self, data):
        endpoint = 'api/v1/maps/geocode'
        response = requests.get(self.base_url + endpoint, json=data)
        location = response.json()
        city = location.get('display_name')
        return location


class StorageService:

    def __init__(self, base_url):
        self.base_url = base_url

    def upload_multiple_files(self, data):
        endpoint = 'api/v1/file/upload/batch'
        response = requests.post(self.base_url + endpoint,
                                 json=data)
        return response
