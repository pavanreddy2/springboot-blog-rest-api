package com.ar.springboot_blog.service.impl;

import com.ar.springboot_blog.dto.CommentDto;
import com.ar.springboot_blog.entity.Comment;
import com.ar.springboot_blog.entity.Post;
import com.ar.springboot_blog.repository.CommentRepository;
import com.ar.springboot_blog.repository.PostRepository;
import com.ar.springboot_blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        //Get the Post Data from the PostRepository
        Post post = postRepository.findById(id).get();
        comment.setPost(post);

        //comment Entity to DB
        Comment newComment = commentRepository.save(comment);
        return mapTODto(newComment);
    }

    @Override
    public List<CommentDto> getAllComment(long postId) {
        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = commentRepository.findAll();
        for(Comment comment: comments){
            CommentDto commentDto = mapTODto(comment);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        CommentDto commentDto = new CommentDto();
        Post post = postRepository.findById(postId).get();

       Comment comment = commentRepository.findById(commentId).get();
       commentDto = mapTODto(comment);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        //retrieve PostId
      Post post = postRepository.findById(postId).get();
      //Retrieve CommentId
      Comment comment = commentRepository.findById(commentId).get();

      if(!comment.getPost().getId().equals(post.getId())){
      }

      comment.setName(commentDto.getName());
      comment.setEmail(commentDto.getEmail());
      comment.setBody(commentDto.getBody());

     Comment updatedComment = commentRepository.save(comment);
        return mapTODto(comment);
    }

    //Create comment to DTO
    private CommentDto mapTODto (Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    //Create DTO to Comment
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
