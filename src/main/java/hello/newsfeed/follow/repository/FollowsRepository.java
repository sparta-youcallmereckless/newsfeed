package hello.newsfeed.follow.repository;

import hello.newsfeed.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowsRepository extends JpaRepository<Follow, Long> {
}
