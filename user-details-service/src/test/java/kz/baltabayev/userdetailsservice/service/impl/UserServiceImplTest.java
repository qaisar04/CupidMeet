package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.exception.RoleAlreadyAssignedException;
import kz.baltabayev.userdetailsservice.exception.UnauthorizedException;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import kz.baltabayev.userdetailsservice.model.types.*;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private UserPreferenceService userPreferenceService;

    @Nested
    @DisplayName("Happy path")
    class HappyPath {

        @Test
        @DisplayName("Test create user successfully")
        public void testCreateUser() {
            Long userId = 1L;
            String username = "qaisario";

            User user = new User(userId, username);
            UserPreference userPreference = new UserPreference(null, PreferredGender.FEMALE, null, null, user);
            UserInfo userInfo = new UserInfo(null, username, 20, "Test", Gender.MALE, PersonalityType.ENFP, "test info", null, user);

            user.setUserPreference(userPreference);
            user.setUserInfo(userInfo);

            when(userRepository.existsById(userId)).thenReturn(false);
            when(userInfoService.create(userInfo, userId)).thenReturn(userInfo);

            assertDoesNotThrow(() -> userService.create(user));
            verify(userRepository, times(1)).insertUser(userId, username);
        }

        @Test
        @DisplayName("Test activate user successfully")
        public void testActivateUser() {
            Long userId = 1L;

            when(userRepository.existsById(userId)).thenReturn(true);

            assertDoesNotThrow(() -> userService.activate(userId));
            verify(userRepository, times(1)).updateUserStatus(userId, Status.ACTIVE);
        }

        @Test
        @DisplayName("Test assign role successfully")
        public void testAssignRole() {
            Long adminId = 0L;
            Long userId = 1L;
            Role role = Role.ADMIN;

            User user = new User();
            user.setId(userId);
            user.setRole(Role.USER);

            User admin = new User();
            admin.setId(adminId);
            admin.setRole(Role.ADMIN);

            when(userRepository.findById(adminId)).thenReturn(Optional.of(admin));
            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userRepository.saveAndFlush(user)).thenReturn(user);

            assertDoesNotThrow(() -> userService.assignRole(adminId, userId, role));
            assertEquals(role, user.getRole());
        }

        @Test
        @DisplayName("Test deactivate user successfully")
        public void testDeactivateUser() {
            Long userId = 1L;

            when(userRepository.existsById(userId)).thenReturn(true);

            assertDoesNotThrow(() -> userService.deactivate(userId));
            verify(userRepository, times(1)).updateUserStatus(userId, Status.INACTIVE);
        }

        @Test
        @DisplayName("Test block user successfully")
        public void testBlockUser() {
            Long userId = 1L;

            when(userRepository.existsById(userId)).thenReturn(true);

            assertDoesNotThrow(() -> userService.block(userId));
            verify(userRepository, times(1)).updateUserStatus(userId, Status.BANNED);
        }
    }

    @Nested
    @DisplayName("Bad path")
    class BadPath {

        @Test
        @DisplayName("Test create user when user already exists")
        public void testCreateUser_EntityAlreadyExistsException() {
            Long userId = 1L;
            String username = "qaisario";

            when(userRepository.existsById(userId)).thenReturn(true);

            EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class,
                    () -> userService.create(new User(userId, username)));
            assertEquals("User already exists with id: " + userId, exception.getMessage());

            verify(userRepository, never()).insertUser(userId, username);
        }

        @Test
        @DisplayName("Test activate user when user not found")
        public void testActivateUser_EntityNotFoundException() {
            Long userId = 1L;

            when(userRepository.existsById(userId)).thenReturn(false);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.activate(userId));
            assertEquals("Not found user with id: " + userId, exception.getMessage());

            verify(userRepository, never()).updateUserStatus(userId, Status.ACTIVE);
        }

        @Test
        @DisplayName("Test assign role when role already assigned")
        public void testAssignRole_RoleAlreadyAssignedException() {
            User firstAdmin = new User();
            firstAdmin.setId(0L);
            firstAdmin.setRole(Role.ADMIN);

            User secondAdmin = new User();
            secondAdmin.setId(1L);
            secondAdmin.setRole(Role.ADMIN);

            when(userRepository.findById(firstAdmin.getId())).thenReturn(Optional.of(firstAdmin));
            when(userRepository.findById(secondAdmin.getId())).thenReturn(Optional.of(secondAdmin));

            RoleAlreadyAssignedException exception = assertThrows(RoleAlreadyAssignedException.class,
                    () -> userService.assignRole(firstAdmin.getId(), secondAdmin.getId(), Role.ADMIN));
            assertEquals("The user already has the specified role.", exception.getMessage());

            assertEquals(Role.ADMIN, secondAdmin.getRole());
            verify(userRepository, never()).saveAndFlush(secondAdmin);
        }

        @Test
        @DisplayName("Test assign role without admin rights")
        public void testAssignRole_UnauthorizedException() {
            User firstUser = new User();
            firstUser.setId(1L);
            firstUser.setRole(Role.USER);

            User secondUser = new User();
            secondUser.setId(1L);
            secondUser.setRole(Role.USER);

            when(userRepository.findById(firstUser.getId())).thenReturn(Optional.of(firstUser));

            UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                    () -> userService.assignRole(firstUser.getId(), secondUser.getId(), Role.ADMIN));
            assertEquals("Admin rights are required to assign roles.", exception.getMessage());

            assertEquals(Role.USER, secondUser.getRole());
            verify(userRepository, never()).saveAndFlush(secondUser);
        }

        @Test
        @DisplayName("Test deactivate user when user not found")
        public void testDeactivateUser_EntityNotFoundException() {
            Long userId = 1L;

            when(userRepository.existsById(userId)).thenReturn(false);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.deactivate(userId));
            assertEquals("Not found user with id: " + userId, exception.getMessage());

            verify(userRepository, never()).updateUserStatus(userId, Status.INACTIVE);
        }
    }
}