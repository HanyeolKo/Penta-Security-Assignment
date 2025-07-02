package com.assignment.pentasecurity_be.domain.post.repository;

import com.assignment.pentasecurity_be.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
