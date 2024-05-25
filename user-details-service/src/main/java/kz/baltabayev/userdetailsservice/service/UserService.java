package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.model.entity.User;

public interface UserService {

    User create(Long id, String username);
}