package com.assignment.pentasecurity_be.domain.post.controller;

import com.assignment.pentasecurity_be.domain.post.dto.PostRequestDto;
import com.assignment.pentasecurity_be.domain.post.dto.PostResponseDto;
import com.assignment.pentasecurity_be.domain.post.service.PostService;
import com.assignment.pentasecurity_be.domain.post.ListType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostRequestDto dto) {
        postService.save(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = ListType.PAGING) String strategy, Pageable pageable) {
        return ResponseEntity.ok(postService.getPostList(strategy, pageable));
    }
}
