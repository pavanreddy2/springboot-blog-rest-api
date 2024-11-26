package com.ar.springboot_blog.repository;

import com.ar.springboot_blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Post findByContent(String content);
}
