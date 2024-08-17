package com.cupidmeet.userdetailsservice.user.service.impl;

import com.cupidmeet.userdetailsservice.message.Messages;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserMatchResponse;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserPreferenceRequest;
import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import com.cupidmeet.userdetailsservice.user.domain.entity.UserInfo;
import com.cupidmeet.userdetailsservice.user.domain.entity.UserPreference;
import com.cupidmeet.userdetailsservice.user.domain.types.PreferredGender;
import com.cupidmeet.userdetailsservice.user.domain.types.Status;
import com.cupidmeet.userdetailsservice.user.repository.UserPreferenceRepository;
import com.cupidmeet.userdetailsservice.user.repository.UserRepository;
import com.cupidmeet.userdetailsservice.user.service.UserPreferenceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;

    private static final int MAX_RESULTS = 10;

    @Override
    public void update(UUID userId, UserPreferenceRequest userPreferenceRequest) {
        UserPreference preference = getByUserId(userId);

        preference.setMaxAge(userPreferenceRequest.getMaxAge());
        preference.setMinAge(userPreferenceRequest.getMinAge());
        preference.setPreferredGender(userPreferenceRequest.getPreferredGender());

        userPreferenceRepository.save(preference);
    }

    @Override
    public List<UserMatchResponse> findMatchingUsers(UUID userId, Set<UUID> excludedUserIds) {
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
                )).collect(Collectors.toList());
    }

    private List<UserInfo> executeQuery(Set<UUID> excludedUserIds, UserPreference preference, UserInfo info, boolean includeCity, boolean includePersonalityType, int limit) {
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

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<UserInfo> root, Set<UUID> excludedUserIds, UserPreference preference, UserInfo info, boolean includeCity, boolean includePersonalityType) {
        List<Predicate> predicates = new ArrayList<>();

        if (excludedUserIds != null && !excludedUserIds.isEmpty()) {
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

        Join<UserInfo, User> userJoin = root.join("user");
        predicates.add(cb.not(userJoin.get("status").in(Status.INACTIVE, Status.BANNED)));

        return predicates;
    }

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

    private UserPreference getByUserId(UUID id) {
        return userPreferenceRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(Messages.NOT_FOUND.getTextPattern(), "Предпочтения пользователя для пользователя", "идентификатором", id)
                ));
    }

    private User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(Messages.NOT_FOUND.getTextPattern(), "Пользователь", "идентификатором", id)
                ));
    }
}