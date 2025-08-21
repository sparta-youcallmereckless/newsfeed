package hello.newsfeed.profile.controller;

import hello.newsfeed.common.consts.Const;
import hello.newsfeed.profile.dto.request.ProfileDeleteRequest;
import hello.newsfeed.user.dto.request.PasswordUpdateRequest;
import hello.newsfeed.profile.dto.request.ProfileCreateRequest;
import hello.newsfeed.profile.dto.request.ProfileUpdateRequest;
import hello.newsfeed.profile.dto.response.ProfileCreateResponse;
import hello.newsfeed.profile.dto.response.ProfileResponse;
import hello.newsfeed.profile.service.ProfileService;
import hello.newsfeed.profile.dto.response.ProfileUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 유저 프로필 생성
    @PostMapping("users/me/profile")
    public ResponseEntity<ProfileCreateResponse> save(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ProfileCreateRequest profileCreateRequest
    ) {
        return ResponseEntity.ok(profileService.save(userId, profileCreateRequest));
    }

    // 유저 프로필 조회
    @GetMapping("/users/me/profile")
    public ResponseEntity<ProfileResponse> getMyProfile(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        return ResponseEntity.ok(profileService.getMyProfile(userId));
    }

    // 특정 유저 프로필 조회
    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<ProfileResponse> getOtherProfile(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(profileService.getOtherProfile(userId));
    }

    // 유저 프로필 수정
    @PutMapping("/users/me/profile")
    public ResponseEntity<ProfileUpdateResponse> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ProfileUpdateRequest userProfileUpdateRequest
    ) {
        return ResponseEntity.ok(profileService.update(userId, userProfileUpdateRequest));
    }

    // 유저 프로필 삭제
    @DeleteMapping("/users/me/profile")
    public void deleteProfile(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ProfileDeleteRequest profileDeleteRequest
    ) {
        profileService.deleteProfile(userId, profileDeleteRequest.getPassword());
    }

}
