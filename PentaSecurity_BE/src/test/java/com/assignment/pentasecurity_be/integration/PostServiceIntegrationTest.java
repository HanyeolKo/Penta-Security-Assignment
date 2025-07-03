package com.assignment.pentasecurity_be.integration;

import com.assignment.pentasecurity_be.domain.post.ListType;
import com.assignment.pentasecurity_be.domain.post.dto.PostInfiniteResponseDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostListResponse;
import com.assignment.pentasecurity_be.domain.post.dto.PostPageResponseDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostResponseDto;
import com.assignment.pentasecurity_be.domain.post.entity.Post;
import com.assignment.pentasecurity_be.domain.post.repository.PostRepository;
import com.assignment.pentasecurity_be.domain.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        postRepository.deleteAll(); // 테스트 간 독립성 보장

        for (int i = 1; i <= 5; i++) {
            postRepository.save(Post.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .author("작성자")
                    .build());
        }
    }


    @Test
    void 게시글_단건_조회() {
        Post saved = postRepository.save(Post.builder()
                .title("단건 제목")
                .content("단건 내용")
                .author("작성자")
                .build());

        PostResponseDto response = postService.getPostById(saved.getId());

        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo("단건 제목");
    }

    @Test
    void 게시글_목록_조회_paging_전략() {
        Pageable pageable = PageRequest.of(0, 3);

        PostListResponse response = postService.getPostList(ListType.PAGING, pageable);

        assertThat(response).isInstanceOf(PostPageResponseDto.class);
        PostPageResponseDto dto = (PostPageResponseDto) response;
        assertThat(dto.posts().size()).isLessThanOrEqualTo(3);
    }

    @Test
    void 게시글_목록_조회_infinite_전략() {
        Pageable pageable = PageRequest.of(0, 3);

        PostListResponse response = postService.getPostList(ListType.INFINITE, pageable);

        assertThat(response).isInstanceOf(PostInfiniteResponseDto.class);
        PostInfiniteResponseDto dto = (PostInfiniteResponseDto) response;
        assertThat(dto.posts().size()).isLessThanOrEqualTo(3);
    }

    @Test
    void 게시글_저장_성공() {
        // given
        PostRequestDto dto =
                new PostRequestDto("통합 제목", "통합 내용", "통합 작성자");

        // when
        postService.save(dto);

        // then
        Post saved = postRepository.findAll().stream()
                .filter(p -> p.getTitle().equals("통합 제목"))
                .findFirst()
                .orElse(null);
        assertThat(saved).isNotNull();
        assertThat(saved.getContent()).isEqualTo("통합 내용");
        assertThat(saved.getAuthor()).isEqualTo("통합 작성자");
    }
}
