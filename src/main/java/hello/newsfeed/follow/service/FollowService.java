package hello.newsfeed.follow.service;

import hello.newsfeed.follow.dto.request.FollowRequestDto;
import hello.newsfeed.follow.dto.response.FollowResponseDto;
import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.repository.FollowsRepository;
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


// TODO 사용자 팔로우
@Transactional
public FollowResponseDto followUser(FollowRequestDto requestDto) {
    Follow follow = new Follow(requestDto.getFollowerId(), requestDto.getFollowingId());
    Follow savedFollow = followsRepository.save(follow);

    return new FollowResponseDto(
            savedFollow.getId(),
            savedFollow.getFollowerId(),
            savedFollow.getFollowingId(),
            null
    );
}
// TODO 사용자 언팔로우
public void unfollowUser(FollowRequestDto requestDto) {
    // 1. 삭제할 팔로우 객체 찾기
    Follow follow = followsRepository.findByFollowerIdAndFollowingId(
            requestDto.getFollowerId(),
            requestDto.getFollowingId()
    );

    if (follow != null) {
        // 2. DB에서 삭제 (하드 삭제 -> 해당 레코드 자체를 완전히 제거)
        followsRepository.delete(follow);
    }
}
// NOTE 5. 팔로잉 리스트 관리
// 특정 사용자의 팔로잉 목록 조회 (내가 팔로우한 사람들)
public List<Long> getFollowingList(Long userId) {
    return followsRepository.findAll().stream() // 1) DB(또는 메모리)에서 모든 Follow 데이터를 가져옴 (그냥 공식처럼 외워야함)
            /**
             * .stream() 사용해서 가져온 List를 Stream으로 변환.
             * 반복문 없이 filter, map 같은 연속 처리를 가능하게 함
             */
            .filter(f -> f.getFollowerId().equals(userId)) // 2) followerId가 내가 찾고 싶은 userId와 같은 데이터만 걸러냄
            /**
             * f = Stream 안의 각 Follow 객체
             * -> = 람다 연산자 (화살표) (“왼쪽(f)이 들어오면 오른쪽 조건(f.getFollowerId().equals(userId))을 평가해라”)
             * f 객체의 followerId가 userId와 같으면 true → filter 통과
             * 다르면 filter에서 제외
             */
            .map(Follow::getFollowingId)  // 3) 걸러진 데이터에서 '내가 팔로우한 사람(followingId)'만 추출
            /**
             *  :: = 메서드 참조(Method Reference)
             *  의미: “Follow 객체 f에 대해 f.getFollowingId() 실행”
             *  따라서 걸러진 Follow 객체를 **followingId 값(Long)**으로 변환
             */
            .collect(Collectors.toList()); // 4) 추출된 followerId들을 List<Long> 형태로 모아서 반환
    /**
     * collect = Stream을 다시 List나 Set 같은 컬렉션으로 변환 (Stream 인터페이스의 최종 연산 메서드)
     * Stream의 최종 결과를 List 형태로 수집
     * Stream 처리 후 최종 List<Long> 반환
     */
}

// 나를 팔로우한 사람들
public List<Long> getFollowerList(Long userId) {
    return followsRepository.findAll().stream()
            .filter(f -> f.getFollowingId().equals(userId))
            .map(Follow::getFollowerId)
            .collect(Collectors.toList());
}

}