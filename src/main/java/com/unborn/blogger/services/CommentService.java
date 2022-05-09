package com.unborn.blogger.services;

import com.unborn.blogger.datatransferobject.CommentDto;

public interface CommentService {

    CommentDto createComment(Long userId, Long postId, CommentDto commentDto);

    CommentDto updateComment(Long id, CommentDto commentDto);

    void deleteComment(Long id);
}
