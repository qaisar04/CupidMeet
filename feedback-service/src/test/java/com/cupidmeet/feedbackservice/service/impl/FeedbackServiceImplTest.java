package com.cupidmeet.feedbackservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cupidmeet.feedbackservice.exception.ThisFeedbackAlreadyExistException;
import com.cupidmeet.feedbackservice.domain.entity.Feedback;
import com.cupidmeet.feedbackservice.domain.type.Grade;
import com.cupidmeet.feedbackservice.repository.FeedbackRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    private final Feedback TEST_FEEDBACK = Feedback.builder()
            .comment("Test comment")
            .grade(Grade.FIVE)
            .userId(1L)
            .build();

    @Before("Set id for test user")
    public void setUp() {
        TEST_FEEDBACK.setId(1L);
    }

    @Nested
    @DisplayName("Happy path")
    class HappyPath {

        @Test
        @DisplayName("Create feedback successfully")
        void createFeedbackSuccess() {
            when(feedbackRepository.existsByUserId(TEST_FEEDBACK.getUserId()))
                    .thenReturn(false);

            assertDoesNotThrow(() -> feedbackService.createFeedback(TEST_FEEDBACK));
            verify(feedbackRepository, times(1)).save(TEST_FEEDBACK);
        }

        @Test
        @DisplayName("Update feedback successfully")
        void updateFeedbackSuccess() {
            Long userId = TEST_FEEDBACK.getUserId();
            String comment = "Test comment for update";
            Grade grade = Grade.FOUR;

            when(feedbackRepository.existsByUserId(userId))
                    .thenReturn(true);
            when(feedbackRepository.findByUserId(userId))
                    .thenReturn(Optional.of(TEST_FEEDBACK));

            assertDoesNotThrow(() -> feedbackService.updateFeedback(userId,
                    new FeedbackRequestResponse(comment, grade)));
            verify(feedbackRepository, times(1)).updateFeedback(comment,
                    grade, TEST_FEEDBACK.getId());
        }
    }

    @Nested
    @DisplayName("Bad path")
    class BadPath {

        @Test
        @DisplayName("Test create feedback when entity already exist")
        void testCreateFeedback_ThisFeedbackAlreadyExistException() {
            when(feedbackRepository.existsByUserId(TEST_FEEDBACK.getUserId()))
                    .thenReturn(true);

            ThisFeedbackAlreadyExistException exception = assertThrows(
                    ThisFeedbackAlreadyExistException.class,
                    () -> feedbackService.createFeedback(TEST_FEEDBACK)
            );
            assertEquals("The feedback with userId: 1 already exists", exception.getMessage());

            verify(feedbackRepository, never()).save(TEST_FEEDBACK);
        }

        @Test
        @DisplayName("Test update feedback when entity doesn't exist")
        void testUpdateFeedback_EntityNotFoundException() {
            Long userId = TEST_FEEDBACK.getUserId();
            String comment = "Test comment for update";
            Grade grade = Grade.FOUR;

            when(feedbackRepository.existsByUserId(userId))
                    .thenReturn(false);

            EntityNotFoundException exception = assertThrows(
                    EntityNotFoundException.class,
                    () -> feedbackService.updateFeedback(userId,
                            new FeedbackRequestResponse(comment, grade))
            );
            assertEquals("Not found feedback with id: 1", exception.getMessage());

            verify(feedbackRepository, never()).updateFeedback(comment, grade,
                    TEST_FEEDBACK.getId());
        }
    }
}