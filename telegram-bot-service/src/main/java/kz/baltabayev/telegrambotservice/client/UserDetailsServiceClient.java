package kz.baltabayev.telegrambotservice.client;

import kz.baltabayev.telegrambotservice.model.payload.UserCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-details-service", path = "/api/v1/user")
public interface UserDetailsServiceClient {

    @PostMapping
    ResponseEntity<Void> createUser(@RequestBody UserCreateRequest request);
}