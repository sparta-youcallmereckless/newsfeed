package hello.newsfeed.comment.repository;

import hello.newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository에 @Repository 포함되어 있기 때문에 안써도 됨
// <클래스명, 기본키>
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시물 ID(postId)에 속한 모든 댓글을 조회하는 메서드
    List<Comment> findByPostId(Long postId);
}
