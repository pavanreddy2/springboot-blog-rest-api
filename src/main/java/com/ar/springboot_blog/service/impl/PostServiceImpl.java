package com.ar.springboot_blog.service.impl;

import com.ar.springboot_blog.dto.PostRequest;
import com.ar.springboot_blog.dto.PostResponse;
import com.ar.springboot_blog.dto.PostResponsePagination;
import com.ar.springboot_blog.entity.Post;
import com.ar.springboot_blog.repository.PostRepository;
import com.ar.springboot_blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository repository;

    public PostServiceImpl(PostRepository postRepository) {
        this.repository = postRepository;
    }

    @Override
    public Post createPost(PostRequest request) {

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setContent(request.getContent());
        repository.save(post);

        return post;
    }

    @Override
    public List<PostResponse> getAllPost() {
        List<Post> posts = repository.findAll();

        List<PostResponse> responses = new ArrayList<>();

        // Iterate through the posts and map each to a PostResponse
        for (Post post : posts) {
            PostResponse response = mapPostToResponse(post);
            responses.add(response);
        }
/*        List<PostResponse> responses1 =  posts.stream().map(this::mapPostToResponse)
                .sorted()
                .collect(Collectors.toList());*/
        return responses;
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Given Post Id: " + id + "Not Found"));
        return mapPostToResponse(post);
    }

    @Override
    public PostResponsePagination getAllPostByPagination(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        List<PostResponse> responses = new ArrayList<>();
        //create the pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = repository.findAll(pageable);
        //get content for page object
        List<Post> listOfPosts = posts.getContent();
        for (Post post : listOfPosts) {
            PostResponse response = mapPostToResponse(post);
            responses.add(response);
        }

        PostResponsePagination responsePagination = new PostResponsePagination();
        responsePagination.setContent(responses);
        responsePagination.setPageNo(posts.getNumber());
        responsePagination.setPageSize(posts.getSize());
        responsePagination.setTotalElements(posts.getTotalElements());
        responsePagination.setTotalPages(posts.getTotalPages());
        responsePagination.setLast(posts.isLast());

        return responsePagination;
    }

    @Override
    public PostResponse getBycontent(String content) {
        Post post = repository.findByContent(content);
        if (post == null){
            throw new RuntimeException("Content not found for: " + content);
        }
        return mapPostToResponse(post);
    }

    @Override
    public PostResponsePagination getPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> page = repository.findAll(pageable);
        List<Post>  posts = page.getContent();

        PostResponsePagination responsePagination = new PostResponsePagination();
        List<PostResponse> postResponseList = new ArrayList<>();
        for (Post post1: posts){
            PostResponse response = new PostResponse();
            response.setId(post1.getId());
            response.setDescription(post1.getDescription());
            response.setContent(post1.getContent());
            response.setDateCreated(post1.getDateCreated());
            response.setLastUpdated(post1.getLastUpdated());

            postResponseList.add(response);
        }
        responsePagination.setContent(postResponseList);
        responsePagination.setPageNo(page.getNumber());
        responsePagination.setPageSize(page.getSize());
        responsePagination.setTotalPages(page.getTotalPages());
        responsePagination.setTotalElements(page.getTotalElements());
        responsePagination.setLast(page.isLast());
        return responsePagination;

    }

    @Override
    public PostResponsePagination getSorting(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        List<Post> posts  = repository.findAll(sort);
        PostResponsePagination responsePagination = new PostResponsePagination();
        List<PostResponse> postResponseList = new ArrayList<>();
        for(Post post: posts){
            PostResponse response = new PostResponse();
            response.setId(post.getId());
            response.setTitle(post.getTitle());
            response.setDescription(post.getDescription());
            response.setContent(post.getContent());
            response.setDateCreated(post.getDateCreated());
            response.setLastUpdated(post.getLastUpdated());
            postResponseList.add(response);
        }
        responsePagination.setContent(postResponseList);
        responsePagination.setPageSize(posts.size());
        return responsePagination;
    }

    private PostResponse mapPostToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setDescription(post.getDescription());
        response.setContent(post.getContent());
        response.setDateCreated(post.getDateCreated());
        response.setLastUpdated(post.getLastUpdated());

        return response;
    }
}
