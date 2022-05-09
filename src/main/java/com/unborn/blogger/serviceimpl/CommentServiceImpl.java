package com.unborn.blogger.serviceimpl;

import com.unborn.blogger.datatransferobject.CommentDto;
import com.unborn.blogger.datatransferobject.PostDto;
import com.unborn.blogger.entities.Comment;
import com.unborn.blogger.entities.Post;
import com.unborn.blogger.entities.User;
import com.unborn.blogger.exceptions.ResourceNotFoundException;
import com.unborn.blogger.repositories.CategoryRepo;
import com.unborn.blogger.repositories.CommentRepo;
import com.unborn.blogger.repositories.PostRepo;
import com.unborn.blogger.repositories.UserRepo;
import com.unborn.blogger.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(Long userId, Long postId, CommentDto commentDto) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        Comment comment = commentDtoToComment(commentDto);
        comment.setUser(user);
        comment.setPost(post);
        Comment createdComment = commentRepo.save(comment);
        return commentToCommentDto(createdComment);
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", id));
        comment.setContent(commentDto.getContent());
        Comment updatedComment = commentRepo.save(comment);
        return commentToCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", id));
        commentRepo.deleteById(id);
    }
    public Comment commentDtoToComment(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }

    public CommentDto commentToCommentDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }
}
