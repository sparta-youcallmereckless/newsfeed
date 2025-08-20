package hello.newsfeed.user.controller;

import hello.newsfeed.user.dto.request.UserCreateRequest;
import hello.newsfeed.user.dto.request.UserUpdateRequest;
import hello.newsfeed.user.dto.response.UserCreateResponse;
import hello.newsfeed.user.dto.response.UserResponse;
import hello.newsfeed.user.dto.response.UserUpdateResponse;
import hello.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성
    // TODO: 회원가입 signup으로 진행
    @PostMapping("/users")
    public ResponseEntity<UserCreateResponse> createUser(
            @RequestBody UserCreateRequest request
    ) {
        return ResponseEntity.ok(userService.save(request));
    }

    // 전체 유저 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 특정 유저 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getOneUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.findOne(userId));
    }

    // 특정 유저 수정 (이메일 수정)
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    // TODO: 회원탈퇴

}