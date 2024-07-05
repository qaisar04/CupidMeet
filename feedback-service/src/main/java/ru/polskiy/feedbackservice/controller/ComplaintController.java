package ru.polskiy.feedbackservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.ComplaintCreateRequest;
import ru.polskiy.feedbackservice.dto.ComplaintResponse;
import ru.polskiy.feedbackservice.mapper.ComplaintMapper;
import ru.polskiy.feedbackservice.service.ComplaintService;

import java.util.List;

/**
 * Controller for managing complaints via REST API.
 * It allows retrieving complaints and adding new ones.
 */
@RestController
@RequestMapping("/api/v1/complaint")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final ComplaintMapper complaintMapper;

    /**
     * Retrieves a list of all complaints.
     *
     * @return A ResponseEntity containing a list of {@link ComplaintResponse} representing all complaints.
     */
    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> findComplaints() {
        return ResponseEntity.ok(
                complaintService.findAllComplaints()
                        .stream()
                        .map(complaintMapper::toResponse)
                        .toList());
    }

    /**
     * Creates a new complaint.
     *
     * @param dto The DTO containing the necessary information for creating a complaint.
     * @return A ResponseEntity with HTTP status 200 (OK) if the creation is successful.
     */
    @PostMapping
    public ResponseEntity<Void> complaintCreate(
            @Valid @RequestBody ComplaintCreateRequest dto
    ) {
        complaintService.createComplaint(complaintMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }
}