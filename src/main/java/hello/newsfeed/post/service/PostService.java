package hello.newsfeed.post.service;

import hello.newsfeed.post.dto.request.PostRequest;
import hello.newsfeed.post.dto.response.PostResponse;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //PostRepository 를 주입받음
    //DB와 연결하여 게시물 데이터를 저장 조회 수정 삭제 하는 역활(데이터를 저장하는 역활은 아님)
    @Transactional
    public PostResponse savePost(Long userId, PostRequest postRequest)
    //게시물 생성 매서드, 사용자로부터 전달받은 PostRequest를 처리함
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        Post post = new Post(
                postRequest.getTitle(),
                postRequest.getContent(),
                user);
        //전달받은 리퀘스트 기준으로 새로운 게시글 생성
        //Post 엔티티 생성자에서 ID,작성일,수정일을 처리
        //제목,내용,작성자는 DTO에서 가져옴
        Post savedPost = postRepository.save(post);
        // repository를 통해 데이터 저장
        // 저장된 데이터 반환받아 값 확보
        return new PostResponse(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getCreatedAt(),
                savedPost.getModifiedAt()
        );//저장된 포스트 엔티티 포스트 리스폰스 디티오로 변환후 반환
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        // pageable 객체에 페이징된 post목록을 조회
        return posts.map(post -> new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()

        ));
        //모든 포스트를 리스폰디티오로 변환후 페이지포스트의 맵 메서드를 사용 하여 각 포스트를 포스트 리스폰 으로 변환하고
        //포스트 리포지트로 반환
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long postId) {
        //단건 게시물 조회
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("없는 게시물입니다.")
        );
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );

        //데이터에서 postId로 조회
        //없다면 런타임 예외 출력
        //조회된 포스트 DTO로 변환후 반환
    }

    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostRequest postRequest) {
        //게시물 수정기능 수행
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("게시물이 없습니다.")
        );
        if (!userId.equals(post.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        post.update(postRequest.getTitle(), postRequest.getContent());
        postRepository.save(post);
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

    @Transactional
    public void deletePost(Long postId, Long userId)
    //게시물 삭제 기능
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("잘못된 입력입니다."));
        //삭제할 게시물조회
        // 없으면 익셉션 발생
        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("잘못된 입력입니다.");
            //요청한 아이디가 게시물 작성자와 다르면 예외 발생
        }
        postRepository.delete(post);
        //조건만족시 삭제
    }
}