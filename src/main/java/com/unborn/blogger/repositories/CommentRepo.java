package com.unborn.blogger.repositories;

import com.unborn.blogger.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {
}
