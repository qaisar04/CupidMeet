package kz.baltabayev.userdetailsservice;

import jakarta.annotation.PostConstruct;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import kz.baltabayev.userdetailsservice.model.types.Status;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;
import java.util.Random;

@EnableFeignClients
@SpringBootApplication
public class UserDetailsServiceApplication {

    private final UserRepository userService;

    @Autowired
    public UserDetailsServiceApplication(UserRepository userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        createUsers();
    }

    private void createUsers() {
        List<String> cities = List.of("Almaty", "Astana", "Shymkent", "Aktobe", "Karaganda", "Taraz", "Pavlodar", "Oskemen", "Kostanay", "Kyzylorda", "Oral", "Petropavl", "Atyrau", "Semey", "Kokshetau", "Taldykorgan");
        PreferredGender preferredGender = PreferredGender.ANY;
        Gender gender = Gender.MALE;
        Random random = new Random();

        for (int i = 0; i < 30; i++) {
            String city = cities.get(random.nextInt(cities.size()));
            int age = 20 + random.nextInt(15);

            User user = new User();
            user.setId((long) i + 1);
            user.setUsername("user" + (i + 1));
            user.setStatus(Status.ACTIVE);

            UserInfo userInfo = new UserInfo();
            userInfo.setName("User " + (i + 1));
            userInfo.setAge(age);
            userInfo.setCity(city);
            userInfo.setGender(gender);
            userInfo.setPersonalityType(PersonalityType.values()[random.nextInt(PersonalityType.values().length)]);
            userInfo.setBio("This is user " + (i + 1));
            userInfo.setUser(user);
            user.setUserInfo(userInfo);

            UserPreference userPreference = new UserPreference();
            userPreference.setPreferredGender(preferredGender);
            userPreference.setMinAge(18);
            userPreference.setMaxAge(35);
            userPreference.setUser(user);
            user.setUserPreference(userPreference);

            userService.save(user);
        }
    }
}