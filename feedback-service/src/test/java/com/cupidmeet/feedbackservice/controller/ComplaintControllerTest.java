package com.cupidmeet.feedbackservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.cupidmeet.feedbackservice.domain.dto.ComplaintCreateRequest;
import com.cupidmeet.feedbackservice.exception.ComplaintToThisUserAlreadyExistException;
import com.cupidmeet.feedbackservice.mapper.ComplaintMapper;
import com.cupidmeet.feedbackservice.domain.entity.Complaint;
import com.cupidmeet.feedbackservice.domain.type.ComplaintType;
import com.cupidmeet.feedbackservice.domain.type.Status;
import com.cupidmeet.feedbackservice.service.ComplaintService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers = ComplaintController.class)
class ComplaintControllerTest {

    @MockBean
    ComplaintMapper complaintMapper;

    @MockBean
    ComplaintService complaintService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        @DisplayName("Test find all complaints endpoint")
        void testFindComplaints() throws Exception {
            Long toUserId = TEST_COMPLAINT.getToUserId();
            String comment = TEST_COMPLAINT.getComment();

            ComplaintResponse response = new ComplaintResponse(toUserId,
                    comment);

            when(complaintService.findAllComplaints()).thenReturn(List.of(TEST_COMPLAINT));
            when(complaintMapper.toResponse(TEST_COMPLAINT)).thenReturn(response);

            mockMvc.perform(get("/api/v1/complaint"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].toUserId").value(toUserId))
                    .andExpect(jsonPath("$[0].comment").value(comment));

            verify(complaintService).findAllComplaints();
        }

        @Test
        @DisplayName("Test find create complaint")
        void testCreateComplaint() throws Exception {
            Long fromUserId = TEST_COMPLAINT.getFromUserId();
            Long toUserId = TEST_COMPLAINT.getToUserId();
            Status status = TEST_COMPLAINT.getStatus();
            String comment = TEST_COMPLAINT.getComment();
            ComplaintType complaintType = TEST_COMPLAINT.getComplaintType();

            ComplaintCreateRequest request =
                    new ComplaintCreateRequest(fromUserId, toUserId, comment, status, complaintType);

            when(complaintMapper.toEntity(request)).thenReturn(TEST_COMPLAINT);

            mockMvc.perform(post("/api/v1/complaint")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            verify(complaintService).createComplaint(TEST_COMPLAINT);
        }
    }

    @Nested
    @DisplayName("Bad path")
    class BadPath {

        @Test
        @DisplayName("Test create complaint that already exist")
        void testCreateComplaint_ComplaintToThisUserAlreadyExistException() throws Exception {
            Long fromUserId = TEST_COMPLAINT.getFromUserId();
            Long toUserId = TEST_COMPLAINT.getToUserId();
            Status status = TEST_COMPLAINT.getStatus();
            String comment = TEST_COMPLAINT.getComment();
            ComplaintType complaintType = TEST_COMPLAINT.getComplaintType();

            ComplaintCreateRequest request =
                    new ComplaintCreateRequest(fromUserId, toUserId, comment, status, complaintType);

            when(complaintMapper.toEntity(request)).thenReturn(TEST_COMPLAINT);
            doThrow(new ComplaintToThisUserAlreadyExistException(fromUserId, toUserId))
                    .when(complaintService).createComplaint(any());

            mockMvc.perform(post("/api/v1/complaint")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail")
                            .value("User with id: 1 already sent complaint to user with id: 2"));

            verify(complaintService).createComplaint(TEST_COMPLAINT);
        }

        @Test
        @DisplayName("Test create complaint to yourself")
        void testCreateComplaintIllegalArgumentException() throws Exception {
            Long fromUserId = TEST_COMPLAINT.getFromUserId();
            Status status = TEST_COMPLAINT.getStatus();
            String comment = TEST_COMPLAINT.getComment();
            ComplaintType complaintType = TEST_COMPLAINT.getComplaintType();

            ComplaintCreateRequest request =
                    new ComplaintCreateRequest(fromUserId, fromUserId, comment, status, complaintType);

            when(complaintMapper.toEntity(request)).thenReturn(TEST_COMPLAINT);
            doThrow(new IllegalArgumentException("Complaint mustn't be sent to yourself"))
                    .when(complaintService).createComplaint(any());

            mockMvc.perform(post("/api/v1/complaint")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail")
                            .value("Complaint mustn't be sent to yourself"));
        }
    }
}