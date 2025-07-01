package com.assignment.pentasecurity_be.domain.post.dto;

import com.assignment.pentasecurity_be.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createAt
){
    public static PostResponseDto from(Post post){
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCreatedAt()
        );
    }
}
