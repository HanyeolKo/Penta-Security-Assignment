package com.assignment.pentasecurity_be.endtoend;

import com.assignment.pentasecurity_be.domain.post.dto.PostInfiniteResponseDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostPageResponseDto;
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
        String url = "http://localhost:" + port + "/post?strategy=paging&page=0&size=10";

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
        String url = "http://localhost:" + port + "/post?strategy=infinity&page=0&size=10";

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
        String url = "http://localhost:" + port + "/post/1";

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

}
