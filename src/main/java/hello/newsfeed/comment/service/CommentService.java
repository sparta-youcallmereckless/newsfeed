package hello.newsfeed.comment.service;

import hello.newsfeed.comment.dto.request.CommentCreateRequest;
import hello.newsfeed.comment.dto.response.CommentCreateResponse;
import hello.newsfeed.comment.dto.response.CommentReadAllResponse;
import hello.newsfeed.comment.entity.Comment;
import hello.newsfeed.comment.repository.CommentRepository;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<CommentReadAllResponse> getAllComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> new CommentReadAllResponse(
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        )).collect(Collectors.toList());
    }
}
