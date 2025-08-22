package hello.newsfeed.follow.repository;

import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowsRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowerAndFollowing(User follower, User following);

    List<Follow> findAllByFollower(User follower);

    List<Follow> findAllByFollowing(User following);
}
