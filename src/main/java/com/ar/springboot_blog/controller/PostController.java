package com.ar.springboot_blog.controller;

import com.ar.springboot_blog.dto.PostRequest;
import com.ar.springboot_blog.dto.PostResponse;
import com.ar.springboot_blog.dto.PostResponsePagination;
import com.ar.springboot_blog.entity.Post;
import com.ar.springboot_blog.service.PostService;
import com.ar.springboot_blog.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class PostController {

    private PostService postService;

    private PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        Post post = postService.createPost(request);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPost() {
        List<PostResponse> response = postService.getAllPost();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("id") Long id) {
        PostResponse response = postService.getPostById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PostResponsePagination> getPage(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        PostResponsePagination responsePagination = postService.getPage(pageNo,pageSize);
        return new ResponseEntity<>(responsePagination,HttpStatus.OK);
    }

    @GetMapping("/sorting")
    public ResponseEntity<PostResponsePagination> getSorting(
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PostResponsePagination responsePagination = postService.getSorting(sortBy,sortDir);
        return new ResponseEntity<>(responsePagination,HttpStatus.OK);
    }

    @GetMapping("/posts/pagination")
    public ResponseEntity<PostResponsePagination> getAllPostByPagination(
        /*    @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir */

            @RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        PostResponsePagination response = postService.getAllPostByPagination(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/postContent")
    public ResponseEntity<PostResponse> getBycontent(@RequestParam("content") String content) {
        PostResponse response = postService.getBycontent(content);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
