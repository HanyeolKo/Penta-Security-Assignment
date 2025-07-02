package com.assignment.pentasecurity_be.domain.post.dto;

import com.assignment.pentasecurity_be.domain.post.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostPageResponseDto(
        List<PostResponseDto> posts,
        int pageNumber,
        int totalPages
) implements PostListResponse {
    public static PostPageResponseDto from(Page<Post> page){
        return new PostPageResponseDto(
                page.map(PostResponseDto::from).getContent(),
                page.getNumber(),
                page.getTotalPages()
        );
    }
}
