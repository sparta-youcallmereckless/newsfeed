package hello.newsfeed.follow.entity;

import hello.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity

// 기본 생성자를 자동 생성, 접근제한은 protected
// JPA가 객체를 생성할 때 필요하지만, 외부에서 기본 생성자를 직접 쓰지 못하게 제한
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// BaseEntity 상속: 생성/수정 시간 등의 공통 필드 포함
public class Follow extends BaseEntity {

    // 기본 키(primary key) 지정
    // 자동 증가 전략: DB가 id 값을 자동으로 생성
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //followerId 필드를 DB 테이블의 컬럼과 연결
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

