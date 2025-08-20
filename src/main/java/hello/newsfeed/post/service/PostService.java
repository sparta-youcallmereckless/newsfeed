package hello.newsfeed.post.service;

import hello.newsfeed.post.dto.request.PostRequest;
import hello.newsfeed.post.dto.response.PostResponse;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    //PostRepository 를 주입받음
    //DB와 연결하여 게시물 데이터를 저장 조회 수정 삭제 하는 역활(데이터를 저장하는 역활은 아님)
    @Transactional
    public PostResponse savePost(PostRequest postRequest)
    //게시물 생성 매서드, 사용자로부터 전달받은 PostRequest를 처리함
    {
        Post post = new Post(postRequest);
        //전달받은 리퀘스트 기준으로 새로운 게시글 생성
        //Post 엔티티 생성자에서 ID,작성일,수정일을 처리
        //제목,내용,작성자는 DTO에서 가져옴
        Post savedPost = postRepository.save(post);
        // repository를 통해 데이터 저장
        // 저장된 데이터 반환받아 값 확보
        return toResponse(savedPost);
        //저장된 포스트 엔티티 포스트 리스폰스 디티오로 변환후 반환
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAllPosts()
    //전체 개시물 조회
    {
        List<Post> posts = postRepository.findAll();
        //데이터에서 모든 포스트 조회
        //리스트 포스트 형태로 반환
        List<PostResponse> postResponses = new ArrayList<>();
        //저장된것을 담을 새로운 리스트 생성
        //이 리스트를 최종적으로 사용자에게 주기
        for (Post post : posts) {
            //조회된 각 포스트를 반복
            postResponses.add(toResponse(post));
        }
        //각 포스트를 리스폰디티오로 변한후 리스트 추가
        return postResponses;
        //모든 포스트를 리스폰디티오로 변환후 리스트 반환
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long postId)
    //단건 게시물 조회
    {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("잘못된 입력입니다."));
        //데이터에서 postId로 조회
        //없다면 런타임 예외 출력
        return toResponse(post);
        //조회된 포스트 DTO로 변환후 반환
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest)
    //게시물 수정기능 수행
    {Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("잘못된 입력입니다."));
        //먼저 기존 게시물을 조회
        //없으면 런타임 예외출력
        post.update(postRequest);
        //포스트 엔티티와 업데이트 메서드로
        Post updated = postRepository.save(post);
        //수정된 내용 데이터베이스 저장
        return toResponse(updated);
    }

@Transactional
public void  deletePost(Long postId, Long userId)
        //게시물 삭제 기능
        {Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("잘못된 입력입니다."));
        //삭제할 게시물조회
        // 없으면 익셉션 발생
        if (!post.getUserId().equals(UserId)){
            throw new RuntimeException("잘못된 입력입니다.");
            //요청한 아이디가 게시물 작성자와 다르면 예외 발생
        }
        postRepository.delete(post);
        //조건만족시 삭제
        }
}
