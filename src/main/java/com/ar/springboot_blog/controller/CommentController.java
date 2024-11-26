package com.ar.springboot_blog.controller;

import com.ar.springboot_blog.dto.CommentDto;
import com.ar.springboot_blog.dto.PostResponse;
import com.ar.springboot_blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long id,
            @RequestBody CommentDto commentDto){
        CommentDto commentDtoResp = commentService.createComment(id, commentDto);
        return new ResponseEntity<>(commentDtoResp, HttpStatus.CREATED);
    }

    /**
     * Get All comment for particular Post
     */
    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable("postId") long postId){
        List<CommentDto> commentDtoList = commentService.getAllComment(postId);
        return new ResponseEntity<>(commentDtoList,HttpStatus.OK);
    }

    /**
     * Get CommentId by PostId
     */
    @GetMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,
                                                     @PathVariable("commentId") long commentId){
        CommentDto commentDto = commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId") long commentId,
                                                    @RequestBody CommentDto commentDto) {

        CommentDto updateComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updateComment,HttpStatus.CREATED);
    }

}

