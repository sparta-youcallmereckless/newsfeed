package hello.newsfeed.profile.controller;

import hello.newsfeed.common.consts.Const;
import hello.newsfeed.profile.dto.request.ProfileCreateRequest;
import hello.newsfeed.profile.dto.response.ProfileCreateResponse;
import hello.newsfeed.profile.dto.response.ProfileResponse;
import hello.newsfeed.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 유저 프로필 생성
    @PostMapping("users/me/profile")
    public ResponseEntity<ProfileCreateResponse> savedProfile(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ProfileCreateRequest profileCreateRequest
            ) {
        return ResponseEntity.ok(profileService.savedProfile(profileCreateRequest));
    }

    // 유저 프로필 조회
    @GetMapping("/users/me/profile")
    public ResponseEntity<ProfileResponse> getMyProfile(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        return ResponseEntity.ok(profileService.getMyProfile(userId));
    }
}
