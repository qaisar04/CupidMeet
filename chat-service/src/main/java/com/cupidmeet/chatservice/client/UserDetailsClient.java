package com.cupidmeet.chatservice.client;

import com.cupidmeet.chatservice.domain.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "user-details-service", path = "api/v1")
public interface UserDetailsClient {

    @PostMapping("/batch")
    ResponseEntity<Map<UUID, UserResponse>> get(Collection<UUID> userIds);
}
