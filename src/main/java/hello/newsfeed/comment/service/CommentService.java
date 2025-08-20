package hello.newsfeed.comment.service;

import hello.newsfeed.comment.dto.request.CommentCreateRequest;
import hello.newsfeed.comment.dto.response.CommentCreateResponse;
import hello.newsfeed.comment.entity.Comment;
import hello.newsfeed.comment.repository.CommentRepository;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    @Transactional
    public CommentCreateResponse createComment(Long userId, Long postId, CommentCreateRequest commentRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );

        Comment comment = new Comment(
                commentRequest.getContent(),
                user,
                post
        );

        Comment savedComment = commentRepository.save(comment);

        return new CommentCreateResponse(
                post.getId(),
                user.getId(),
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }
}
