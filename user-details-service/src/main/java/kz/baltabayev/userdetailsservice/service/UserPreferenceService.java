package kz.baltabayev.userdetailsservice.service;

public interface UserPreferenceService {

    void create(Long userId, String gender);

    void update(Long userId, String gender, Integer maxAge, Integer minAge);
}