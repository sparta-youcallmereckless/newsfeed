package hello.newsfeed.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowResponseDto {

    private Long id;          // 팔로우 관계의 ID (DB에서 자동 생성됨)
    private Long followerId;  // 팔로우 한 사람
    private Long followingId; // 팔로우 당한 사람

}
