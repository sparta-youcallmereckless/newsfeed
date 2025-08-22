package hello.newsfeed.follow.controller;


import hello.newsfeed.follow.dto.request.FollowRequestDto;
import hello.newsfeed.follow.dto.response.FollowResponseDto;
import hello.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor

public class FollowController {

    // Service 객체 의존성 주입
    private final FollowService followService;

    // 사용자 팔로우
    @PostMapping
    public ResponseEntity<FollowResponseDto> follow(@RequestBody FollowRequestDto requestDto) {

        // Service 계층에게 "팔로우 처리"를 시킴
        // followUser()는 DB 저장 같은 실제 로직을 수행하고 결과를 FollowResponseDto 로 반환함
        FollowResponseDto dto = followService.followUser(requestDto);

        // 메시지 출력
        dto.setMessage(requestDto.getFollowerId() + "님이" + requestDto.getFollowingId() + "님을" + "팔로우 합니다");

        // ResponseEntity.ok() : 응답 코드 200(성공)과 함께 dto 객체를 반환
        return ResponseEntity.ok(dto);
    }

    // 사용자 언팔로우
    @DeleteMapping
    public ResponseEntity<FollowResponseDto> unfollow(@RequestBody FollowRequestDto requestDto) {

        // Service 계층에게 "언팔로우 처리"를 시킴
        followService.unfollowUser(requestDto);

        // 응답으로 보낼 DTO를 직접 생성
        FollowResponseDto dto = new FollowResponseDto(
                null, // id 값은 null (DB 저장이 필요 없으니까)
                requestDto.getFollowerId(),
                requestDto.getFollowingId(),
                requestDto.getFollowingId() + "를 언팔로우 했습니다."
        );

        // 최종 응답 반환 (200 OK + dto)
        return ResponseEntity.ok(dto);
    }

    // 내가 팔로우한 사람들 조회
    @GetMapping("/following/{userId}")
    public List<Long> getFollowingList(@PathVariable Long userId) {
        return followService.getFollowingList(userId);
    }

    // 나를 팔로우한 사람들 조회
    @GetMapping("/followers/{userId}")
    public List<Long> getFollowerList(@PathVariable Long userId) {
        return followService.getFollowerList(userId);


    }
}


