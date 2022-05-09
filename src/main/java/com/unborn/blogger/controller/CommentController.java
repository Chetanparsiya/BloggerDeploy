package com.unborn.blogger.controller;

import com.unborn.blogger.datatransferobject.ApiResponse;
import com.unborn.blogger.datatransferobject.CommentDto;
import com.unborn.blogger.repositories.CommentRepo;
import com.unborn.blogger.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("post/{postId}/user/{userId}/comments")
    ResponseEntity<CommentDto> createComment(@PathVariable Long postId, @PathVariable Long userId, @RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.createComment(postId,userId,commentDto);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.OK);
    }

    @PutMapping("comments/{id}")
    ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.updateComment(id,commentDto);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Succefully", true), HttpStatus.OK);
    }


}
