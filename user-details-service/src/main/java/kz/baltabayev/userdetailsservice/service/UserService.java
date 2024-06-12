package kz.baltabayev.userdetailsservice.service;


import kz.baltabayev.userdetailsservice.model.entity.User;

public interface UserService {

    void create(Long id, String username);

    void deactivate(Long id);

    User get(Long id);
}