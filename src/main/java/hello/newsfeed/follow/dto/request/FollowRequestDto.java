package hello.newsfeed.follow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FollowRequestDto {
    private Long followerId;  // 팔로우 하는 사용자 ID
    private Long followingId; // 팔로우 당하는 사용자 ID
}
