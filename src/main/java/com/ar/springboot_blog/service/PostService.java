package com.ar.springboot_blog.service;

import com.ar.springboot_blog.dto.PostRequest;
import com.ar.springboot_blog.dto.PostResponse;
import com.ar.springboot_blog.dto.PostResponsePagination;
import com.ar.springboot_blog.entity.Post;

import java.util.List;


public interface PostService {

    Post createPost(PostRequest request);

    List<PostResponse> getAllPost();

    PostResponse getPostById(Long id);

    PostResponsePagination getAllPostByPagination(int pageNo, int pageSize,String sortBy,String sortDir);

    PostResponse getBycontent(String content);

    PostResponsePagination getPage(int pageNo, int pageSize);

    PostResponsePagination getSorting(String sortBy, String sortDir);
}
