package com.assignment.pentasecurity_be.unit;

import com.assignment.pentasecurity_be.domain.post.ListType;
import com.assignment.pentasecurity_be.domain.post.dto.*;
import com.assignment.pentasecurity_be.domain.post.service.PostService;
import com.assignment.pentasecurity_be.domain.post.service.strategy.PostListStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceUnitTest {

    @Mock
    private Map<String, PostListStrategy> strategyMap;

    @Mock
    private PostListStrategy mockStrategy;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 전략_이름으로_게시글_목록_가져오기_성공() {
        // given
        String strategy = ListType.PAGING;
        Pageable pageable = PageRequest.of(0, 10);

        PostPageResponseDto mockResponse = new PostPageResponseDto(
                List.of(new PostResponseDto(1L, "제목", "내용", "작성자", null)),
                0, 10
        );

        when(strategyMap.get(strategy)).thenReturn(mockStrategy);
        when(mockStrategy.loadPosts(pageable)).thenReturn(mockResponse);

        // when
        PostListResponse result = postService.getPostList(strategy, pageable);

        // then
        assertThat(result).isInstanceOf(PostPageResponseDto.class);
        verify(strategyMap).get(strategy);
        verify(mockStrategy).loadPosts(pageable);
    }

    @Test
    void 지원하지_않는_전략_예외() {
        // given
        String invalidStrategy = "unknown";
        Pageable pageable = PageRequest.of(0, 10);

        when(strategyMap.get(invalidStrategy)).thenReturn(null);

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> postService.getPostList(invalidStrategy, pageable)
        );
    }
}