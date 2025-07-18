package com.assignment.pentasecurity_be.domain.post.service.strategy;

import com.assignment.pentasecurity_be.domain.post.dto.PostListResponse;
import org.springframework.data.domain.Pageable;

public interface LoadStrategy {

    PostListResponse loadPosts(Pageable pageable);

}
