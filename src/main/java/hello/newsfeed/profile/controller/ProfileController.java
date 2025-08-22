package hello.newsfeed.profile.controller;

import hello.newsfeed.common.consts.Const;
import hello.newsfeed.profile.dto.request.ProfileDeleteRequest;
import hello.newsfeed.profile.dto.request.ProfileCreateRequest;
import hello.newsfeed.profile.dto.request.ProfileUpdateRequest;
import hello.newsfeed.profile.dto.response.ProfileCreateResponse;
import hello.newsfeed.profile.dto.response.ProfileResponse;
import hello.newsfeed.profile.service.ProfileService;
import hello.newsfeed.profile.dto.response.ProfileUpdateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /*
       기능: 유저 프로필 생성 (Create Profile)
       HTTP Method: POST
       URL: /users/me/profile (http://localhost:8080/users/me/profile)
       인증/인가: 로그인 필요 (세션 기반)
       Request:
        @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
        @RequestBody ProfileCreateRequest profileCreateRequest: 생성할 프로필 정보
       Response: 성공 시 생성된 프로필 정보를 JSON 형태로 반환
   */
    @PostMapping("/users/me/profile")
    public ResponseEntity<ProfileCreateResponse> save(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ProfileCreateRequest profileCreateRequest
    ) {
        return ResponseEntity.ok(profileService.save(userId, profileCreateRequest));
    }

    /*
            기능: 내 프로필 조회 (Read My Profile)
            HTTP Method: GET
            URL: /users/me/profile (http://localhost:8080/users/me/profile)
            인증/인가: 로그인 필요 (세션 기반)
            Request:
             @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
            Response: 성공 시 로그인한 사용자의 프로필 정보를 JSON 형태로 반환
        */
    @GetMapping("/users/me/profile")
    public ResponseEntity<ProfileResponse> getMyProfile(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        return ResponseEntity.ok(profileService.getMyProfile(userId));
    }

    /*
           기능: 특정 유저 프로필 조회 (Read Other User Profile)
           HTTP Method: GET
           URL: /users/{userId}/profile (http://localhost:8080/users/{userId}/profile)
           인증/인가: 없음 (공개 API)
           Request:
            @PathVariable Long userId: 조회할 유저의 ID
           Response: 성공 시 해당 유저의 프로필 정보를 JSON 형태로 반환
       */
    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<ProfileResponse> getOtherProfile(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(profileService.getOtherProfile(userId));
    }

    /*
           기능: 유저 프로필 수정 (Update My Profile)
           HTTP Method: PUT
           URL: /users/me/profile (http://localhost:8080/users/me/profile)
           인증/인가: 로그인 필요 (세션 기반)
           Request:
            @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
            @RequestBody ProfileUpdateRequest userProfileUpdateRequest: 수정할 프로필 정보
           Response: 성공 시 수정된 프로필 정보를 JSON 형태로 반환
       */
    @PutMapping("/users/me/profile")
    public ResponseEntity<ProfileUpdateResponse> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody ProfileUpdateRequest userProfileUpdateRequest
    ) {
        return ResponseEntity.ok(profileService.update(userId, userProfileUpdateRequest));
    }

    /*
        기능: 유저 프로필 삭제 (Delete My Profile)
        HTTP Method: DELETE
        URL: /users/me/profile (http://localhost:8080/users/me/profile)
        인증/인가: 로그인 필요 (세션 기반)
        Request:
         @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
         @RequestBody ProfileDeleteRequest profileDeleteRequest: 비밀번호 확인용 요청 객체
        Response: 삭제 성공 시 HTTP 200 OK 반환
    */
    @DeleteMapping("/users/me/profile")
    public void deleteProfile(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody ProfileDeleteRequest profileDeleteRequest
    ) {
        profileService.deleteProfile(userId, profileDeleteRequest.getPassword());
    }

}
