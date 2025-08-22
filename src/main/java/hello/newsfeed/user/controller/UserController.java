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

    /*
    기능: 전체 유저 조회 (Read All Users)
    HTTP Method: GET
    URL: /users (http://localhost:8080/users)
    인증/인가: 없음 (공개 API)
    Request: 없음
    Response: 성공 시 모든 유저 정보를 리스트 형태로 JSON으로 반환
*/
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /*
    기능: 특정 유저 조회 (Read One User)
    HTTP Method: GET
    URL: /users/{userId} (http://localhost:8080/users/{userId})
    인증/인가: 없음 (공개 API)
    Request:
     @PathVariable Long userId: 조회할 유저의 ID
    Response: 성공 시 해당 유저의 정보를 JSON 형태로 반환
*/
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getOneUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.findOne(userId));
    }

    /*
        기능: 유저 정보 수정 (Update User Info - email)
        HTTP Method: PATCH
        URL: /users (http://localhost:8080/users)
        인증/인가: 로그인 필요 (세션 기반)
        Request:
         @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
         @RequestBody UserUpdateRequest request: 수정할 유저 정보 (email)
        Response: 성공 시 수정된 유저 정보를 JSON 형태로 반환
    */
    @PatchMapping("/users")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    /*
    기능: 유저 비밀번호 수정 (Update User Password)
    HTTP Method: PATCH
    URL: /users/me/password (http://localhost:8080/users/me/password)
    인증/인가: 로그인 필요 (세션 기반)
    Request:
     @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
     @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest: 기존 비밀번호 및 새 비밀번호 정보
    Response: 성공 시 HTTP 200 OK 반환
*/
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