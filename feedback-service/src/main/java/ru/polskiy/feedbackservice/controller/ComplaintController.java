package ru.polskiy.feedbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.ComplaintCreate;
import ru.polskiy.feedbackservice.dto.ComplaintRequest;
import ru.polskiy.feedbackservice.exception.CreateComplaintException;
import ru.polskiy.feedbackservice.mapper.ComplaintMapper;
import ru.polskiy.feedbackservice.service.ComplaintService;

import java.util.List;
import java.util.Optional;

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
     * @return A list of {@link ComplaintCreate} representing all complaints.
     */
    @GetMapping("/all")
    public List<ComplaintCreate> findComplaints(
    ) {
        return complaintService.findAllComplaints()
                .stream()
                .map(complaintMapper::toDto)
                .toList();
    }

    /**
     * Creates a new complaint.
     *
     * @param dto    The DTO containing the necessary information for creating a complaint.
     * @return The created complaint as a {@link ComplaintCreate} object. Returns null if the creation fails.
     */
    @PostMapping("/create")
    public ResponseEntity<ComplaintCreate> complaintCreate(
            @RequestBody ComplaintRequest dto
    ) {
        return Optional.of(new ComplaintCreate(dto.userId(),
                        dto.toUserId(), dto.comment(),
                        null,
                        dto.type()))
                .map(complaintMapper::toEntity)
                .map(entity->{
                    complaintService.createComplaint(entity);
                    return ResponseEntity.ok(complaintMapper.toDto(entity));
                }).orElseThrow(()-> new CreateComplaintException(dto.toString()));
    }
}
