package com.assignment.pentasecurity_be.domain.post.controller;

import com.assignment.pentasecurity_be.domain.post.dto.PostListRequestDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostResponseDto;
import com.assignment.pentasecurity_be.domain.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Validated PostRequestDto dto) {
        postService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("id") @Validated @Min(1) long id) {

        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@ModelAttribute @Validated PostListRequestDto request) {
        Pageable pageable = PageRequest.of(request.page(), request.size());
        return ResponseEntity.ok(postService.getPostList(request.type(), pageable));
    }
}
