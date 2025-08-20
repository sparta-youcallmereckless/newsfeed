package hello.newsfeed.follow.entity;

import hello.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long followerId; // 팔로우 하는 사람 (나)

    @Column(nullable = false)
    private Long followingId; // 팔로우 당하는 사람 (상대방)

    // 생성자: 팔로우 관계를 새로 만들 때 사용
    public Follow(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}

