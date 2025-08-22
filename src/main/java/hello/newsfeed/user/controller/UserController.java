package hello.newsfeed.user.controller;

import hello.newsfeed.common.consts.Const;
import hello.newsfeed.user.dto.request.PasswordUpdateRequest;
import hello.newsfeed.user.dto.request.UserCreateRequest;
import hello.newsfeed.user.dto.request.UserUpdateRequest;
import hello.newsfeed.user.dto.response.UserCreateResponse;
import hello.newsfeed.user.dto.response.UserResponse;
import hello.newsfeed.user.dto.response.UserUpdateResponse;
import hello.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponse> createUser(
            @Valid @RequestBody UserCreateRequest request
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

    // 유저 정보 수정 (이메일 수정)
    @PatchMapping("/users")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    // 유저 비밀번호 수정
    @PatchMapping("/users/me/password")
    public ResponseEntity<Void> updatePassword(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest
    ) {
        return ResponseEntity.ok(userService.updatePassword(userId, passwordUpdateRequest));
    }

    // 회원 탈퇴
    @DeleteMapping("/users")
    public void deleteUser(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        Long userIdFromSession = (Long) session.getAttribute("LOGIN_USER");
        userService.deleteById(userIdFromSession);
        session.invalidate();
    }

}