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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service                        // Bean으로 등록할 때 쓰는 annotation
@RequiredArgsConstructor        // 필수(final) 필드만을 매개변수로 하는 생성자를 자동으로 생성함
public class CommentService {
    // 스프링 컨테이너로부터 의존성을 주입받는 싱글톤 CommentRepository, UserRepository, PostRepository 객체 (나 이 객체 사용할래! 스프링 컨테이너: 알겠어 써!)
    // 싱글톤: 어떤 클래스를 하나의 객체로 만드는 것
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    @Transactional                      // 트랜잭션을 관리하기 위해 사용하는 annotation
    public CommentCreateResponse createComment(Long userId, Long postId, CommentCreateRequest commentRequest) {
        // 유저 ID가 일치하는 User 엔티티를 조회함
        // 일치하는 User 엔티티가 없으면 예외를 발생시킴
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        // 게시물 ID가 일치하는 Post 엔티티를 조회함
        // 일치하는 Post 엔티티가 없으면 예외를 발생시킴
        Post post = findPostById(postId);

        // CommentCreateRequest(DTO)를 Comment 엔티티 객체로 변환함 (Builder 패턴 사용)
        Comment comment = commentRequest.toEntity(post, user);

        // save 메서드를 활용하여 Comment 엔티티를 데이터베이스에 저장하고, 저장된 객체를 반환함
        Comment savedComment = commentRepository.save(comment);

        // 저장된 Comment 엔티티를 CommentCreateResponse(DTO)로 변환하여 반환함
        return CommentCreateResponse.from(savedComment);
    }

    // 댓글 전체 조회
    @Transactional(readOnly = true)      // 트랜잭션을 관리하기 위해 사용하는 annotation (읽기 전용)
    public List<CommentReadAllResponse> getAllComments(Long postId) {
        // postId에 해당하는 Comment 엔티티를 조회하여 리스트로 저장함
        List<Comment> comments = commentRepository.findByPostId(postId);

        // Comment 엔티티 리스트를 CommentReadAllResponse(DTO) 리스트로 변환하여 반환함
        return comments.stream().map(CommentReadAllResponse::from).collect(Collectors.toList());
    }

    // 댓글 단건 조회
    @Transactional(readOnly = true)     // 트랜잭션을 관리하기 위해 사용하는 annotation (읽기 전용)
    public CommentReadSingleResponse getComment(Long postId, Long commentId) {
        // 게시물 ID가 일치하는 Post 엔티티를 조회함
        // 일치하는 Post 엔티티가 없으면 예외를 발생시킴
        Post post = findPostById(postId);

        // 댓글 ID가 일치하는 Comment 엔티티를 조회함
        // 일치하는 Comment 엔티티가 없으면 예외를 발생시킴
        Comment comment = findCommentById(commentId);

        // 전달받은 게시물 ID와 댓글이 속한 게시물 ID가 일치하지 않으면
        if (!post.getId().equals(comment.getPost().getId())) {
            // 예외(IllegalArgumentException)를 발생시킴
            throw new IllegalArgumentException("게시글에 해당 댓글이 존재하지 않습니다.");
        }

        // 저장된 Comment 엔티티를 CommentReadSingleResponse(DTO)로 변환하여 반환함
        return CommentReadSingleResponse.from(comment);
    }

    // 댓글 수정 기능
    @Transactional                      // 트랜잭션을 관리하기 위해 사용하는 annotation
    public CommentUpdateResponse updateComment(Long userId, Long commentId, CommentUpdateRequest commentUpdateRequest) {
        // 댓글 ID가 일치하는 Comment 엔티티를 조회함
        // 일치하는 Comment 엔티티가 없으면 예외를 발생시킴
        Comment comment = findCommentById(commentId);

        // 댓글 작성자 ID와 전달받은 사용자 ID가 일치하지 않으면
        if (!comment.getUser().getId().equals(userId)) {
            // 예외(IllegalArgumentException)를 발생시킴
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }

        // Comment 엔티티의 댓글 내용(content)을 요청 DTO의 값으로 수정함
        comment.update(commentUpdateRequest.getContent());

        // 저장된 Comment 엔티티를 CommentUpdateResponse(DTO)로 변환하여 반환함
        return CommentUpdateResponse.from(comment);
    }

    // 댓글 삭제 기능
    @Transactional                      // 트랜잭션을 관리하기 위해 사용하는 annotation
    public void deleteComment(Long userId, Long commentId) {
        // 댓글 ID가 일치하는 Comment 엔티티를 조회함
        // 일치하는 Comment 엔티티가 없으면 예외를 발생시킴
        Comment comment = findCommentById(commentId);

        // 댓글 작성자(userId) 또는 댓글이 달린 게시글의 작성자(userId)가 아닌 경우
        if (!comment.getUser().getId().equals(userId) || !comment.getPost().getUser().getId().equals(userId)) {
            // 예외(IllegalArgumentException)를 발생시킴
            throw new IllegalArgumentException("댓글 작성자 또는 게시글 작성자만 삭제할 수 있습니다.");
        }

        // commentId 해당하는 Comment 엔티티를 데이터베이스에서 삭제함
        commentRepository.delete(comment);
    }

    // 주어진 postId로 게시물을 조회하는 메서드
    // 존재하지 않을 경우, IllegalArgumentException을 발생시킴
    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );
    }

    // 주어진 commentId로 댓글을 조회하는 메서드
    // 존재하지 않을 경우, IllegalArgumentException을 발생시킴
    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
    }
}
