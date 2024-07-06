package ru.polskiy.feedbackservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all complaints", description = "Retrieves a list of all complaints.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of complaints", content = @Content(schema = @Schema(implementation = ComplaintResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
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
    @Operation(summary = "Create a new complaint", description = "Creates a new complaint based on the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the complaint", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> complaintCreate(
            @Valid @RequestBody ComplaintCreateRequest dto
    ) {
        complaintService.createComplaint(complaintMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }
}