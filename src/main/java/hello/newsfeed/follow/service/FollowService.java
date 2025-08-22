package hello.newsfeed.follow.service;

import hello.newsfeed.follow.dto.request.FollowRequestDto;
import hello.newsfeed.follow.dto.response.FollowResponseDto;
import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.repository.FollowsRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class FollowService {

    private final FollowsRepository followsRepository;
    private final UserRepository userRepository;

    @Transactional
    public FollowResponseDto followUser(FollowRequestDto requestDto) {
        User follower = userRepository.findById(requestDto.getFollowerId())
                .orElseThrow(() -> new IllegalArgumentException("팔로우하는 사용자가 존재하지 않습니다."));
        User following = userRepository.findById(requestDto.getFollowingId())
                .orElseThrow(() -> new IllegalArgumentException("팔로우 대상 사용자가 존재하지 않습니다."));

        Follow follow = new Follow(follower, following);
        Follow savedFollow = followsRepository.save(follow);

        return new FollowResponseDto(
                savedFollow.getId(),
                follower.getId(),
                following.getId(),
                null
    );
}
// TODO 사용자 언팔로우
public void unfollowUser(FollowRequestDto requestDto) {

    User follower = userRepository.findById(requestDto.getFollowerId())
            .orElseThrow(() -> new IllegalArgumentException("팔로우하는 사용자가 존재하지 않습니다."));
    User following = userRepository.findById(requestDto.getFollowingId())
            .orElseThrow(() -> new IllegalArgumentException("팔로우 대상 사용자가 존재하지 않습니다."));
// 1. 삭제할 팔로우 객체 찾기
    Follow follow = followsRepository.findByFollowerAndFollowing(follower, following);
    if (follow != null) {
        // 2. DB에서 삭제 (하드 삭제 -> 해당 레코드 자체를 완전히 제거)
        followsRepository.delete(follow);
    }
}
// NOTE 5. 팔로잉 리스트 관리
// 특정 사용자의 팔로잉 목록 조회 (내가 팔로우한 사람들)
public List<Long> getFollowingList(Long userId) {
    User follower = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    return followsRepository.findAllByFollower(follower)
            .stream()
            .map(f -> f.getFollowing().getId())// 실제 following User의 ID 추출
            .collect(Collectors.toList());
}

// 나를 팔로우한 사람들
public List<Long> getFollowerList(Long userId) {
    User following = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    return followsRepository.findAllByFollowing(following)
            .stream()
            .map(f -> f.getFollower().getId()) // 실제 follower User의 ID 추출
            .collect(Collectors.toList());
}

}