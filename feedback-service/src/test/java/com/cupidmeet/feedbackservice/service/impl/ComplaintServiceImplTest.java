package com.cupidmeet.feedbackservice.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cupidmeet.feedbackservice.exception.ComplaintToThisUserAlreadyExistException;
import com.cupidmeet.feedbackservice.domain.entity.Complaint;
import com.cupidmeet.feedbackservice.domain.type.ComplaintType;
import com.cupidmeet.feedbackservice.domain.type.Status;
import com.cupidmeet.feedbackservice.repository.ComplaintRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceImplTest {

    @InjectMocks
    private ComplaintServiceImpl complaintService;

    @Mock
    private ComplaintRepository complaintRepository;

    private final Complaint TEST_COMPLAINT = Complaint.builder()
            .comment("test comment")
            .complaintType(ComplaintType.OTHER)
            .fromUserId(1L)
            .toUserId(2L)
            .status(Status.NEW)
            .build();

    @Nested
    @DisplayName("Happy path")
    class HappyPath {

        @Test
        @DisplayName("Test create complaint successfully")
        void testCreateComplaintSuccess() {
            when(complaintRepository.existsByFromUserIdAndToUserId(1L, 2L)).thenReturn(false);

            assertDoesNotThrow(() -> complaintService.createComplaint(TEST_COMPLAINT));
            verify(complaintRepository, times(1)).save(TEST_COMPLAINT);
        }
    }

    @Nested
    @DisplayName("Bad path")
    class BadPath {

        @Test
        @DisplayName("Test create entity when complaint to this user already exist")
        void testCreateComplaint_complaintToThisUserAlreadyExistException() {
            when(complaintRepository.existsByFromUserIdAndToUserId(1L, 2L)).thenReturn(true);

            ComplaintToThisUserAlreadyExistException exception = assertThrows(
                    ComplaintToThisUserAlreadyExistException.class,
                    () -> complaintService.createComplaint(TEST_COMPLAINT)
            );
            assertEquals("User with id: 1 already sent complaint to user with id: 2", exception.getMessage());

            verify(complaintRepository, never()).save(TEST_COMPLAINT);
        }
    }
}