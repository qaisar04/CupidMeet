package kz.baltabayev.usermatchingservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_rating")
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "rated_user_ids", joinColumns = @JoinColumn(name = "rated_users_ids_id"))
    @Column(name = "rated_user_id")
    private List<Long> ratedUserIds = new LinkedList<>();
}