package kz.baltabayev.userdetailsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.baltabayev.userdetailsservice.mapper.UserMapper;
import kz.baltabayev.userdetailsservice.model.dto.UserResponse;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.types.Status;
import kz.baltabayev.userdetailsservice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Happy path")
    class HappyPath {

        @Test
        @DisplayName("Test create user endpoint")
        public void createUser() {
            // after refactoring
        }

        @Test
        @DisplayName("Test activate user endpoint")
        public void testActivateUser() throws Exception {
            Long userId = 1L;

            doNothing().when(userService).activate(userId);

            mockMvc.perform(patch("/api/v1/user/{userId}/activate", userId))
                    .andExpect(status().isOk());

            verify(userService).activate(userId);
        }

        @Test
        @DisplayName("Test deactivate user endpoint")
        public void testDeactivateUser() throws Exception {
            Long userId = 1L;

            doNothing().when(userService).deactivate(userId);

            mockMvc.perform(patch("/api/v1/user/{userId}/deactivate", userId))
                    .andExpect(status().isOk());

            verify(userService).deactivate(userId);
        }

        @Test
        @DisplayName("Test get user details endpoint")
        public void getUser() throws Exception {
            Long userId = 1L;
            User user = new User();
            user.setId(userId);
            user.setUsername("qaisario");

            when(userService.get(userId)).thenReturn(user);

            UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), Status.ACTIVE, null, null);
            when(userMapper.toResponse(user)).thenReturn(userResponse);

            mockMvc.perform(get("/api/v1/user/{userId}", userId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(userId))
                    .andExpect(jsonPath("$.username").value("qaisario"));


            verify(userService, times(1)).get(userId);
        }
    }

    @Nested
    @DisplayName("Bad path")
    class BadPath {
        // Tests for error handling scenarios can be added here
    }
}