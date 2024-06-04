package kz.baltabayev.telegrambotservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserState {

    @Id
    private Long userId;
    private String name;
    private Integer age;
    private String gender;
    private String city;
    private String about;
    private String language;
    private String mbti;

    public UserState(Long userId) {
        this.userId = userId;
    }

    public boolean isProfileComplete() {
        return name != null && !name.isEmpty() && age != null;
    }
}