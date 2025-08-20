package hello.newsfeed.user.controller;

import hello.newsfeed.user.dto.request.UserCreateRequest;
import hello.newsfeed.user.dto.response.UserCreateResponse;
import hello.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성
    @PostMapping("/users")
    public ResponseEntity<UserCreateResponse> createUser(
            @RequestBody UserCreateRequest request
    ) {
        return ResponseEntity.ok(userService.save(request));
    }
}

