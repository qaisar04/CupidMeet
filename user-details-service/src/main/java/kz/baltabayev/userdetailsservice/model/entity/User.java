package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    private Long id;

    private String username;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserPreference userPreference;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInfo userInfo;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}