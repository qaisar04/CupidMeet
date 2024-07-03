package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.dto.UserMatchResponse;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import kz.baltabayev.userdetailsservice.repository.UserPreferenceRepository;
import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserService userService;
    private final UserPreferenceRepository userPreferenceRepository;
    private final EntityManager entityManager;

    public static final String NOT_FOUND_MESSAGE = "Not found userPreference for the user with id: ";
    public static final String ALREADY_EXISTS_MESSAGE = "UserPreference already exists for user with id: ";

    @Override
    public void create(Long userId, String gender) {
        if (userPreferenceRepository.existsByUserId(userId)) {
            throw new EntityAlreadyExistsException(ALREADY_EXISTS_MESSAGE + userId);
        }

        PreferredGender preferredGender = PreferredGender.fromString(gender);
        User user = userService.get(userId);
        Integer age = user.getUserInfo().getAge();
        UserPreference userPreference = new UserPreference(preferredGender, age + 3, age - 3, user);
        userPreferenceRepository.save(userPreference);
    }

    @Override
    public void update(Long userId, String gender, Integer maxAge, Integer minAge) {
        UserPreference preference = getByIdUserId(userId);
        PreferredGender preferredGender = PreferredGender.fromString(gender);

        preference.setPreferredGender(preferredGender);
        preference.setMaxAge(maxAge);
        preference.setMinAge(minAge);

        userPreferenceRepository.save(preference);
    }

    @Override
    public List<UserMatchResponse> findMatchingUsers(Long userId, Set<Long> excludedUserIds) {
        User user = userService.get(userId);
        UserPreference preference = user.getUserPreference();
        UserInfo info = user.getUserInfo();

        excludedUserIds.add(userId);

        int remainingResults = 10;

        List<UserInfo> step1Results = executeQuery(userId, excludedUserIds, preference, info, true, true);
        Set<UserInfo> userInfos = new LinkedHashSet<>(step1Results);
        remainingResults = updateRemainingResults(userInfos, step1Results, remainingResults, excludedUserIds);

        if (remainingResults > 0) {
            List<UserInfo> step2Results = executeQuery(userId, excludedUserIds, preference, info, false, true);
            userInfos.addAll(step2Results);
            remainingResults = updateRemainingResults(userInfos, step2Results, remainingResults, excludedUserIds);
        }

        if (remainingResults > 0) {
            List<UserInfo> step3Results = executeQueryWithAgeVariation(userId, excludedUserIds, preference, info);
            userInfos.addAll(step3Results);
            remainingResults = updateRemainingResults(userInfos, step3Results, remainingResults, excludedUserIds);
        }

        if (remainingResults > 0) {
            List<UserInfo> step4Results = executeQuery(userId, excludedUserIds, preference, info, false, false);
            userInfos.addAll(step4Results);
            updateRemainingResults(userInfos, step4Results, remainingResults, excludedUserIds);
        }

        return userInfos.stream()
                .limit(10)
                .map(userInfo -> new UserMatchResponse(
                        userInfo.getUser().getId(),
                        userInfo.getName(),
                        userInfo.getCity(),
                        userInfo.getAge(),
                        userInfo.getPersonalityType(),
                        userInfo.getBio(),
                        userInfo.getFiles().stream().map(FileAttachment::getPath).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }

    private int updateRemainingResults(Set<UserInfo> userInfos, List<UserInfo> newResults, int remainingResults, Set<Long> excludedUserIds) {
        int initialSize = userInfos.size();
        userInfos.addAll(newResults);
        int newSize = userInfos.size();
        excludedUserIds.addAll(newResults.stream().map(ui -> ui.getUser().getId()).collect(Collectors.toSet()));
        return remainingResults - (newSize - initialSize);
    }

    private List<UserInfo> executeQuery(Long userId, Set<Long> excludedUserIds, UserPreference preference, UserInfo info, boolean includeCity, boolean includePersonalityType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserInfo> query = cb.createQuery(UserInfo.class);
        Root<UserInfo> root = query.from(UserInfo.class);

        List<Predicate> predicates = buildPredicates(cb, root, excludedUserIds, userId, preference, info, includeCity, includePersonalityType);

        query.select(root).where(predicates.toArray(new Predicate[0]));

        TypedQuery<UserInfo> typedQuery = entityManager.createQuery(query);
        return typedQuery.setMaxResults(10).getResultList();
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<UserInfo> root, Set<Long> excludedUserIds, Long userId, UserPreference preference, UserInfo info, boolean includeCity, boolean includePersonalityType) {
        List<Predicate> predicates = new ArrayList<>();

        if (excludedUserIds != null && !excludedUserIds.isEmpty()) {
            excludedUserIds.add(userId);
            predicates.add(root.get("user").get("id").in(excludedUserIds).not());
        }

        if (includeCity) {
            predicates.add(cb.equal(root.get("city"), info.getCity()));
        }
        if (includePersonalityType) {
            predicates.add(cb.equal(root.get("personalityType"), info.getPersonalityType()));
        }

        Predicate genderPredicate = getGenderPredicate(cb, root, preference, info);
        predicates.add(genderPredicate);

        predicates.add(cb.between(root.get("age"), preference.getMinAge(), preference.getMaxAge()));

        return predicates;
    }

    private Predicate getGenderPredicate(CriteriaBuilder cb, Root<UserInfo> root, UserPreference preference, UserInfo info) {
        if (preference.getPreferredGender() == PreferredGender.ANY) {
            Join<UserInfo, User> userJoin = root.join("user");
            Join<User, UserPreference> userPreferenceJoin = userJoin.join("userPreference");
            return cb.equal(userPreferenceJoin.get("preferredGender"), root.get("gender"));
        } else {
            Join<UserInfo, User> userJoin = root.join("user");
            Join<User, UserPreference> userPreferenceJoin = userJoin.join("userPreference");
            return cb.and(
                    cb.equal(root.get("gender"), preference.getPreferredGender()),
                    cb.equal(userPreferenceJoin.get("preferredGender"), info.getGender())
            );
        }
    }

    private List<UserInfo> executeQueryWithAgeVariation(Long userId, Set<Long> excludedUserIds, UserPreference preference, UserInfo info) {
        List<UserInfo> results = new ArrayList<>();
        int ageVariation = 1;

        while (results.size() < 10 && preference.getMinAge() - ageVariation >= 0) {
            UserPreference ageVariedPreference = new UserPreference(
                    preference.getPreferredGender(),
                    preference.getMaxAge(),
                    preference.getMinAge() - ageVariation,
                    null
            );
            results.addAll(executeQuery(userId, excludedUserIds, ageVariedPreference, info, true, false));
            ageVariation++;
        }

        return results;
    }

    private UserPreference getByIdUserId(Long userId) {
        return userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + userId));
    }
}