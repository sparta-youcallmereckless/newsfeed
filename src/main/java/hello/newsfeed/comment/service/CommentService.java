package hello.newsfeed.comment.service;

import hello.newsfeed.comment.dto.request.CommentCreateRequest;
import hello.newsfeed.comment.dto.request.CommentUpdateRequest;
import hello.newsfeed.comment.dto.response.CommentCreateResponse;
import hello.newsfeed.comment.dto.response.CommentReadAllResponse;
import hello.newsfeed.comment.dto.response.CommentReadSingleResponse;
import hello.newsfeed.comment.dto.response.CommentUpdateResponse;
import hello.newsfeed.comment.entity.Comment;
import hello.newsfeed.comment.repository.CommentRepository;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    // 댓글 단건 조회
    @Transactional(readOnly = true)
    public CommentReadSingleResponse getComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        if(!post.getId().equals(comment.getPost().getId())) {
            throw new IllegalArgumentException("게시글에 해당 댓글이 존재하지 않습니다.");
        }

        return new CommentReadSingleResponse(
                post.getId(),
                comment.getUser().getId(),
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 댓글 수정 기능
    @Transactional
    public CommentUpdateResponse updateComment(Long userId, Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        if(!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }

        comment.update(commentUpdateRequest.getContent());

        return new CommentUpdateResponse(
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 댓글 삭제 기능
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        if(!comment.getUser().getId().equals(userId) || !comment.getPost().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자 또는 게시글 작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);

    }
}
