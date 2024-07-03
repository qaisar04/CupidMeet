package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.repository.UserPreferenceRepository;
import kz.baltabayev.userdetailsservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserPreferenceServiceImplTest {

    @InjectMocks
    private UserPreferenceServiceImpl userPreferenceService;

    @Mock
    private UserService userService;

    @Mock
    UserPreferenceRepository userPreferenceRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<UserInfo> criteriaQuery;

    @Mock
    private Root<UserInfo> root;

    @Mock
    private TypedQuery<UserInfo> typedQuery;

    @BeforeEach
    void setUp() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(UserInfo.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(UserInfo.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    }
}
