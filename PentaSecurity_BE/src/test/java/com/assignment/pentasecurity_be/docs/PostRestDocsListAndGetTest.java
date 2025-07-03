package com.assignment.pentasecurity_be.docs;

import com.assignment.pentasecurity_be.domain.post.entity.Post;
import com.assignment.pentasecurity_be.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostRestDocsListAndGetTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    private Long postId;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();
        Post post = postRepository.save(Post.builder()
                .title("문서화 단건 제목")
                .content("문서화 단건 내용")
                .author("문서화 단건 작성자")
                .build());
        postId = post.getId();
    }

    @Test
    @DisplayName("게시글 단건조회 API 문서화")
    void getPostDocs() throws Exception {
        mockMvc.perform(get("/posts/{id}", postId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("id").description("게시글 ID"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("author").description("작성자"),
                                fieldWithPath("createAt").description("작성일시")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 리스트조회 API 문서화")
    void listPostDocs() throws Exception {
        mockMvc.perform(get("/posts?type=paging&page=0&size=10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-list",
                        responseFields(
                                fieldWithPath("posts").description("게시글 목록"),
                                fieldWithPath("posts[].id").description("게시글 ID"),
                                fieldWithPath("posts[].title").description("제목"),
                                fieldWithPath("posts[].content").description("내용"),
                                fieldWithPath("posts[].author").description("작성자"),
                                fieldWithPath("posts[].createAt").description("작성일시"),
                                fieldWithPath("pageNumber").description("페이지 번호"),
                                fieldWithPath("totalPages").description("페이지 크기")
                        )
                ));
    }
}

