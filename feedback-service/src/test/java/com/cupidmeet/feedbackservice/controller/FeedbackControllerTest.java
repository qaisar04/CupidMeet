package com.cupidmeet.feedbackservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackCreateRequest;
import com.cupidmeet.feedbackservice.exception.ThisFeedbackAlreadyExistException;
import com.cupidmeet.feedbackservice.mapper.FeedbackMapper;
import com.cupidmeet.feedbackservice.domain.entity.Feedback;
import com.cupidmeet.feedbackservice.domain.type.Grade;
import com.cupidmeet.feedbackservice.service.FeedbackService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeedbackController.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FeedbackMapper feedbackMapper;

    @MockBean
    FeedbackService feedbackService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Feedback TEST_FEEDBACK = Feedback.builder()
            .comment("test comment")
            .grade(Grade.FOUR)
            .userId(1L)
            .build();

    @Nested
    @DisplayName("Happy path")
    class HappyPath {

        @Test
        @DisplayName("Test find all feedback endpoint")
        void testFindFeedbacks() throws Exception {
            Grade grade = TEST_FEEDBACK.getGrade();
            String comment = TEST_FEEDBACK.getComment();

            FeedbackRequestResponse response =
                    new FeedbackRequestResponse(comment, grade);

            when(feedbackMapper.toResponse(TEST_FEEDBACK)).thenReturn(response);
            when(feedbackService.findAllFeedbacks()).thenReturn(List.of(TEST_FEEDBACK));

            mockMvc.perform(get("/api/v1/feedback"))
                    .andExpect(jsonPath("$[0].grade").value(grade.name()))
                    .andExpect(jsonPath("$[0].comment").value(comment))
                    .andExpect(status().isOk());


            verify(feedbackService).findAllFeedbacks();
        }

        @Test
        @DisplayName("Test feedback create endpoint")
        void testCreateFeedback() throws Exception {
            Grade grade = TEST_FEEDBACK.getGrade();
            Long userId = TEST_FEEDBACK.getUserId();
            String comment = TEST_FEEDBACK.getComment();

            FeedbackCreateRequest request =
                    new FeedbackCreateRequest(userId, comment, grade);

            when(feedbackMapper.toEntity(request)).thenReturn(TEST_FEEDBACK);

            mockMvc.perform(post("/api/v1/feedback")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            verify(feedbackService).createFeedback(TEST_FEEDBACK);
        }

        @Test
        @DisplayName("Test feedback update endpoint")
        void testUpdateFeedback() throws Exception {
            Grade grade = TEST_FEEDBACK.getGrade();
            Long userId = TEST_FEEDBACK.getUserId();
            String comment = TEST_FEEDBACK.getComment();

            FeedbackRequestResponse request =
                    new FeedbackRequestResponse(comment, grade);

            mockMvc.perform(patch("/api/v1/feedback/update/{userId}", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            verify(feedbackService).updateFeedback(userId, request);
        }
    }

    @Nested
    @DisplayName("Bad path")
    class BadPath {

        @Test
        @DisplayName("Test create feedback that already exist")
        void testCreateFeedback_ThisFeedbackAlreadyExistException() throws Exception {
            Grade grade = TEST_FEEDBACK.getGrade();
            Long userId = TEST_FEEDBACK.getUserId();
            String comment = TEST_FEEDBACK.getComment();

            FeedbackCreateRequest request =
                    new FeedbackCreateRequest(userId, comment, grade);

            when(feedbackMapper.toEntity(request)).thenReturn(TEST_FEEDBACK);
            doThrow(new ThisFeedbackAlreadyExistException(userId.toString()))
                    .when(feedbackService).createFeedback(any());

            mockMvc.perform(post("/api/v1/feedback")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail")
                            .value("The feedback with userId: 1 already exists"));

            verify(feedbackService).createFeedback(TEST_FEEDBACK);
        }

        @Test
        @DisplayName("Test feedback update when feedback doesn't exist")
        void testUpdateFeedback() throws Exception {
            Grade grade = TEST_FEEDBACK.getGrade();
            Long userId = TEST_FEEDBACK.getUserId();
            String comment = TEST_FEEDBACK.getComment();

            FeedbackRequestResponse request =
                    new FeedbackRequestResponse(comment, grade);

            doThrow(new EntityNotFoundException("Not found feedback with id: " + userId))
                    .when(feedbackService).updateFeedback(any(), any());

            mockMvc.perform(patch("/api/v1/feedback/update/{userId}", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.detail")
                            .value("Not found feedback with id: " + userId));
        }
    }
}