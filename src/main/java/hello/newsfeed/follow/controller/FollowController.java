package hello.newsfeed.follow.controller;


import hello.newsfeed.follow.dto.request.FollowRequestDto;
import hello.newsfeed.follow.dto.response.FollowResponseDto;
import hello.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows") // 이 컨트롤러에서 제공하는 API의 기본 URL 경로를 지정 (즉, "/api/follows"로 시작하는 요청은 이 컨트롤러가 처리)
@RequiredArgsConstructor //NOTE: 스프링이 자동으로 생성자 주입(Dependency Injection) 맨날 까먹고 생성자 또 쓰는데 그러지 마라..

public class FollowController {

    private final FollowService followService;


    // TODO 팔로우
    @PostMapping
    public ResponseEntity<FollowResponseDto> follow(@RequestBody FollowRequestDto requestDto) {
        FollowResponseDto dto = followService.followUser(requestDto);
        dto.setMessage("사용자 " + requestDto.getFollowingId() + "를 팔로우 했습니다!");
        return ResponseEntity.ok(dto);
    }

    // TODO 언팔로우
    @DeleteMapping
    public ResponseEntity<FollowResponseDto> unfollow(@RequestBody FollowRequestDto requestDto) {
        followService.unfollowUser(requestDto);

        FollowResponseDto dto = new FollowResponseDto(
                null,
                requestDto.getFollowerId(),
                requestDto.getFollowingId(),
                "사용자 " + requestDto.getFollowingId() + "를 언팔로우 했습니다!"
        );

        return ResponseEntity.ok(dto);
    }
}
    /**&
     @PathVariable 와 @RequestBody 차이점 확인 // .pahth 할때 필터의 아이디를 빼서 써올수 있나 // SessionAttributions ID 참고

            //요청 바디(JSON 형식)에 들어 있는 followRequestDto 데이터를 꺼내옴. 이 안에는 "누구를 팔로우할지 (followeeId)" 정보가 들어 있음.
            @RequestBody FollowRequestDto followRequestDto
            **//
    //


