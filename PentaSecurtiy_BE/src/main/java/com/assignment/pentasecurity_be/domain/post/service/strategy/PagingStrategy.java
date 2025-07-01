package com.assignment.pentasecurity_be.domain.post.service.strategy;

import com.assignment.pentasecurity_be.domain.post.ListType;
import com.assignment.pentasecurity_be.domain.post.dto.PostPageResponseDto;
import com.assignment.pentasecurity_be.domain.post.entity.Post;
import com.assignment.pentasecurity_be.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component(ListType.PAGING)
@RequiredArgsConstructor
public class PagingStrategy implements PostListStrategy {

    private final PostRepository postRepository;

    @Override
    public PostPageResponseDto loadPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return PostPageResponseDto.from(posts);
    }
}
