package com.assignment.pentasecurity_be.domain.post.service;

import com.assignment.pentasecurity_be.domain.post.dto.PostListResponse;
import com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostResponseDto;
import com.assignment.pentasecurity_be.domain.post.entity.Post;
import com.assignment.pentasecurity_be.domain.post.repository.PostRepository;
import com.assignment.pentasecurity_be.domain.post.service.strategy.LoadStrategy;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final Map<String, LoadStrategy> strategyMap;

    public PostListResponse getPostList(String strategy, Pageable pageable) {
        // 서비스 로직상 2중 예외 처리
        LoadStrategy selected = Optional.ofNullable(strategyMap.get(strategy)).orElseThrow(
                () -> new IllegalArgumentException("지원하지 않는 타입 입니다.")
        );

        return selected.loadPosts(pageable);
    }

    public PostResponseDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));        // 404
        return PostResponseDto.from(post);
    }

    public void save(PostRequestDto dto){
        Post post = Post.builder()
                .title(dto.title())
                .content(dto.content())
                .author(dto.author())
                .build();
        postRepository.save(post);
    }

}
