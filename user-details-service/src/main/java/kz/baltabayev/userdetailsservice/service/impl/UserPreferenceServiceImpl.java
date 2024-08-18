package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import kz.baltabayev.userdetailsservice.model.dto.UserMatchResponse;
import kz.baltabayev.userdetailsservice.model.entity.Compatibility;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import kz.baltabayev.userdetailsservice.model.types.Status;
import kz.baltabayev.userdetailsservice.repository.UserPreferenceRepository;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserPreferenceService} providing operations related to user preferences.
 */
@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;

    public static final String NOT_FOUND_MESSAGE = "Not found userPreference for the user with id: ";
    private static final int MAX_RESULTS = 10;

    /**
     * Updates the user preference for the specified user.
     *
     * @param userId the ID of the user for whom the preference is updated
     * @param gender the preferred gender as a string
     * @param maxAge the maximum age preference
     * @param minAge the minimum age preference
     */
    @Override
    public void update(Long userId, String gender, Integer maxAge, Integer minAge) {
        UserPreference preference = getByIdUserId(userId);
        PreferredGender preferredGender = PreferredGender.fromString(gender);

        preference.setPreferredGender(preferredGender);
        preference.setMaxAge(maxAge);
        preference.setMinAge(minAge);

        userPreferenceRepository.save(preference);
    }

    /**
     * Finds matching users based on the user's preferences.
     *
     * @param userId          the ID of the user for whom matching users are sought
     * @param excludedUserIds set of user IDs to be excluded from matching
     * @return a list of {@link UserMatchResponse} containing details of the matching users
     */
    @Override
    public List<UserMatchResponse> findMatchingUsers(Long userId, Set<Long> excludedUserIds) {
        User user = getUserById(userId);
        UserPreference preference = user.getUserPreference();
        UserInfo info = user.getUserInfo();

        if (excludedUserIds == null) {
            excludedUserIds = new HashSet<>();
        }
        excludedUserIds.add(userId);

        int limit = 10;

        List<UserInfo> step1Results = executeQuery(excludedUserIds, preference, info, true, true, limit);
        Set<UserInfo> userInfos = new LinkedHashSet<>(step1Results);
        limit -= step1Results.size();

        if (limit > 0) {
            List<UserInfo> step2Results = executeQuery(excludedUserIds, preference, info, true, false, limit);
            userInfos.addAll(step2Results);
            limit -= step2Results.size();
        }

        if (limit > 0) {
            List<UserInfo> step3Results = executeQuery(excludedUserIds, preference, info, false, false, limit);
            userInfos.addAll(step3Results);
        }

        return userInfos.stream()
                .limit(MAX_RESULTS)
                .map(userInfo -> new UserMatchResponse(
                        userInfo.getUser().getId(),
                        userInfo.getName(),
                        userInfo.getCity(),
                        userInfo.getAge(),
                        userInfo.getPersonalityType(),
                        userInfo.getBio(),
                        userInfo.getFileIds()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Executes a query to find user information based on preferences.
     *
     * @param excludedUserIds        set of user IDs to be excluded from matching
     * @param preference             user preference criteria
     * @param info                   user information criteria
     * @param includeCity            flag indicating whether to include city in the criteria
     * @param includePersonalityType flag indicating whether to include personality type in the criteria
     * @return a list of {@link UserInfo} matching the criteria
     */
    private List<UserInfo> executeQuery(Set<Long> excludedUserIds, UserPreference preference, UserInfo info, boolean includeCity, boolean includePersonalityType, int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserInfo> query = cb.createQuery(UserInfo.class);
        Root<UserInfo> root = query.from(UserInfo.class);

        List<Predicate> predicates = buildPredicates(cb, root, excludedUserIds, preference, info, includeCity, includePersonalityType);

        query.select(root).where(predicates.toArray(new Predicate[0]));

        query.orderBy(
                cb.asc(cb.equal(root.get("city"), info.getCity())),
                cb.asc(cb.equal(root.get("personalityType"), info.getPersonalityType()))
        );

        TypedQuery<UserInfo> typedQuery = entityManager.createQuery(query);

        return typedQuery.setMaxResults(limit).getResultList();
    }

    /**
     * Builds predicates based on the criteria for user matching.
     *
     * @param cb                     CriteriaBuilder instance
     * @param root                   Root entity for the criteria query
     * @param excludedUserIds        set of user IDs to be excluded from matching
     * @param preference             user preference criteria
     * @param info                   user information criteria
     * @param includeCity            flag indicating whether to include city in the criteria
     * @param includePersonalityType flag indicating whether to include personality type in the criteria
     * @return a list of {@link Predicate} representing the criteria for user matching
     */
    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<UserInfo> root, Set<Long> excludedUserIds, UserPreference preference, UserInfo info, boolean includeCity, boolean includePersonalityType) {
        List<Predicate> predicates = new ArrayList<>();

        if (excludedUserIds != null && !excludedUserIds.isEmpty()) {
            predicates.add(root.get("user").get("id").in(excludedUserIds).not());
        }

        if (includeCity) {
            predicates.add(cb.equal(root.get("city"), info.getCity()));
        }

//        Subquery<Integer> maxGradeSubquery = cb.createQuery().subquery(Integer.class);
//        Root<Compatibility> compatibilityRoot = maxGradeSubquery.from(Compatibility.class);
//        maxGradeSubquery.select(cb.greatest(compatibilityRoot.get("grade")));
//
//        Subquery<String> personalityTypeSubquery = cb.createQuery().subquery(String.class);
//        Root<Compatibility> compabilityRoot2 = personalityTypeSubquery.from(Compatibility.class);
//        personalityTypeSubquery.select(compabilityRoot2.get("personalityType"))
//                .where(cb.equal(compabilityRoot2.get("grade"), maxGradeSubquery));

        Subquery<Integer> maxGradeSubquery = cb.createQuery().subquery(Integer.class);
        Root<Compatibility> compatibilityRoot = maxGradeSubquery.from(Compatibility.class);
        maxGradeSubquery.select(cb.max(compatibilityRoot.get("grade")));

        // Подзапрос для получения personalityType с максимальным значением grade
        Subquery<String> personalityTypeSubquery = cb.createQuery().subquery(String.class);
        Root<Compatibility> compatibilityRoot2 = personalityTypeSubquery.from(Compatibility.class);
        personalityTypeSubquery.select(compatibilityRoot2.get("personalityType"))
                .where(cb.equal(compatibilityRoot2.get("grade"), maxGradeSubquery));
        //TODO think about this logic
        //TODO add data of types in liquibase

        if (includePersonalityType) {
//            predicates.add(cb.equal(root.get("personalityType"), info.getPersonalityType()));
            predicates.add(root.get("personalityType").in(personalityTypeSubquery));
        }

        Predicate genderPredicate = getGenderPredicate(cb, root, preference, info);
        predicates.add(genderPredicate);

        predicates.add(cb.between(root.get("age"), preference.getMinAge(), preference.getMaxAge()));

        Join<UserInfo, User> userJoin = root.join("user");
        predicates.add(cb.not(userJoin.get("status").in(Status.INACTIVE, Status.BANNED)));

        return predicates;
    }

    /**
     * Constructs a gender-based predicate for querying user information based on preference criteria.
     *
     * @param cb         CriteriaBuilder instance to build predicates
     * @param root       Root entity representing UserInfo for querying
     * @param preference UserPreference object containing preferred gender and age criteria
     * @param info       UserInfo object representing current user's information
     * @return Predicate representing the gender matching criteria for the query
     */
    private Predicate getGenderPredicate(CriteriaBuilder cb, Root<UserInfo> root, UserPreference preference, UserInfo info) {
        if (preference.getPreferredGender() == PreferredGender.ANY) {
            Join<UserInfo, User> userJoin = root.join("user");
            Join<User, UserPreference> userPreferenceJoin = userJoin.join("userPreference");
            return cb.or(
                    cb.equal(userPreferenceJoin.get("preferredGender"), info.getGender()),
                    cb.equal(userPreferenceJoin.get("preferredGender"), PreferredGender.ANY)
            );
        } else {
            Join<UserInfo, User> userJoin = root.join("user");
            Join<User, UserPreference> userPreferenceJoin = userJoin.join("userPreference");
            return cb.and(
                    cb.equal(root.get("gender"), preference.getPreferredGender()),
                    cb.equal(userPreferenceJoin.get("preferredGender"), info.getGender())
            );
        }
    }

    /**
     * Retrieves the user preference by user ID.
     *
     * @param userId the ID of the user
     * @return the {@link UserPreference} entity corresponding to the user ID
     * @throws EntityNotFoundException if no user preference is found for the specified user ID
     */
    private UserPreference getByIdUserId(Long userId) {
        return userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + userId));
    }

    /**
     * Retrieves the {@link User} entity associated with the given ID.
     *
     * @param id The ID of the user to retrieve {@link User} for.
     * @return The {@link User} entity if found.
     * @throws EntityNotFoundException If no {@link User} entity is found for the given ID.
     */
    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }
}