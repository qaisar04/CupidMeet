package kz.baltabayev.telegrambotservice.client;

import kz.baltabayev.telegrambotservice.model.payload.UserCreateRequest;
import kz.baltabayev.telegrambotservice.model.payload.UserInfoCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-details-service", path = "/api/v1/user")
public interface UserDetailsServiceClient {

    @PostMapping("/create")
    ResponseEntity<Void> create(
            @RequestBody UserCreateRequest request
    );

    @PostMapping("{userId}/info/create")
    ResponseEntity<Void> create(
            @RequestBody UserInfoCreateRequest request,
            @PathVariable("userId") Long userId
    );
}