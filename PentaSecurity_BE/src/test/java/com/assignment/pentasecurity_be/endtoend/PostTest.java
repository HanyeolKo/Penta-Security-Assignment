package com.assignment.pentasecurity_be.endtoend;

import com.assignment.pentasecurity_be.domain.post.dto.PostInfiniteResponseDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostPageResponseDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostResponseDto;
import com.assignment.pentasecurity_be.domain.post.entity.Post;
import com.assignment.pentasecurity_be.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();

        for(int i = 1; i <= 5; i++){
            postRepository.save(Post.builder()
                            .title("Title" + i)
                            .content("Content" + i)
                            .author("Author" + i)
                            .build()
            );
        }
    }

    @Test
    void 게시글_페이징_조회(){
        String url = "http://localhost:" + port + "/posts?type=paging&page=0&size=10";

        ResponseEntity<PostPageResponseDto> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );

        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().posts().size()).isEqualTo(5);
        assertThat(response.getBody().posts().getFirst().title()).startsWith("Title");
    }

    @Test
    void 게시글_무한스크롤_조회(){
        String url = "http://localhost:" + port + "/posts?type=infinity&page=0&size=10";

        ResponseEntity<PostInfiniteResponseDto> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );

        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().posts().size()).isEqualTo(5);
        assertThat(response.getBody().posts().getFirst().title()).startsWith("Title");
    }

    @Test
    void 게시글_단건_조회(){
        // 첫 번째 게시글의 id를 동적으로 조회
        Long firstPostId = postRepository.findAll().get(0).getId();
        String url = "http://localhost:" + port + "/posts/" + firstPostId;

        ResponseEntity<PostResponseDto> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );

        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().title()).startsWith("Title");
    }

    @Test
    void type_파라미터_제약조건_위반_테스트() {
        String url = "http://localhost:" + port + "/posts?type=1&page=0&size=10";
        ResponseEntity<PostInfiniteResponseDto> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void page_파라미터_음수_제약조건_위반_테스트() {
        String url = "http://localhost:" + port + "/posts?type=paging&page=-1&size=10";
        ResponseEntity<PostInfiniteResponseDto> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void size_파라미터_최대값_초과_제약조건_위반_테스트() {
        String url = "http://localhost:" + port + "/posts?type=paging&page=0&size=101";
        ResponseEntity<PostInfiniteResponseDto> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void 게시글_저장_성공() {
        String url = "http://localhost:" + port + "/posts";
        com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto dto =
                new com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto("e2e 제목", "e2e 내용", "e2e 작성자");

        ResponseEntity<Void> response = this.restTemplate.postForEntity(url, dto, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // 저장된 게시글이 실제로 존재하는지 확인
        boolean exists = postRepository.findAll().stream()
                .anyMatch(p -> p.getTitle().equals("e2e 제목") && p.getContent().equals("e2e 내용") && p.getAuthor().equals("e2e 작성자"));
        assertThat(exists).isTrue();
    }

    @Test
    void 게시글_저장_실패_필수값_누락() {
        String url = "http://localhost:" + port + "/posts";
        // 제목 누락
        PostRequestDto dto = new com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto("", "e2e 내용", "e2e 작성자");
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, dto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // 내용 누락
        dto = new com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto("e2e 제목", "", "e2e 작성자");
        response = this.restTemplate.postForEntity(url, dto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // 작성자 누락
        dto = new com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto("e2e 제목", "e2e 내용", "");
        response = this.restTemplate.postForEntity(url, dto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
