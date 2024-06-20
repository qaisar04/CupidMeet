package ru.polskiy.feedbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.ComplaintCreateDTO;
import ru.polskiy.feedbackservice.dto.ComplaintRequestDTO;
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
     * @return A list of {@link ComplaintCreateDTO} representing all complaints.
     */
    @GetMapping("/all")
    public List<ComplaintCreateDTO> findComplaints() {

        return complaintService.findAllComplaints()
                .stream()
                .map(complaintMapper::toDto)
                .toList();
    }

    /**
     * Creates a new complaint.
     *
     * @param dto    The DTO containing the necessary information for creating a complaint.
     * @param userId The ID of the user who is filing the complaint. This is obtained from the path variable.
     * @return The created complaint as a {@link ComplaintCreateDTO} object. Returns null if the creation fails.
     */
    @PostMapping("/create/{userId}")
    public ComplaintCreateDTO complaintCreate(
            @RequestBody ComplaintRequestDTO dto,
            @PathVariable Long userId
    ) {
        return Optional.of(new ComplaintCreateDTO(userId,
                        dto.toUserId(), dto.comment(),
                        null,
                        null))
                .map(complaintMapper::toEntity)
                .map(complaintService::createComplaint)
                .map(complaintMapper::toDto)
                .orElse(null);
    }
}
