package com.cupidmeet.feedbackservice.controller;

import com.cupidmeet.feedbackservice.domain.dto.ComplaintCreateRequest;
import com.cupidmeet.feedbackservice.domain.dto.ComplaintResponse;
import com.cupidmeet.feedbackservice.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/complaint")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @Operation(operationId = "complaintGetList", summary = "Получить список всех жалоб")
    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> get() {
        return ResponseEntity.ok(complaintService.get());
    }

    @Operation(operationId = "complaintGetById", summary = "Получить жалобу по идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(complaintService.get(id));
    }

    @Operation(operationId = "createComplaint", summary = "Создать жалобу")
    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody ComplaintCreateRequest request
    ) {
        complaintService.create(request);
        return ResponseEntity.ok().build();
    }
}