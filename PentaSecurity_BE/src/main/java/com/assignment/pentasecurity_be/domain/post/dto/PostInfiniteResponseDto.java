package com.assignment.pentasecurity_be.domain.post.dto;

import com.assignment.pentasecurity_be.domain.post.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostInfiniteResponseDto(
        List<PostResponseDto> posts,
        boolean hasMore
) implements PostListResponse {
    public static PostInfiniteResponseDto from(Page<Post> page){
        return new PostInfiniteResponseDto(
                page.map(PostResponseDto::from).getContent(),
                page.hasNext()
        );
    }
}
