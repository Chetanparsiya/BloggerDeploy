package com.unborn.blogger.repositories;

import com.unborn.blogger.entities.Category;
import com.unborn.blogger.entities.Post;
import com.unborn.blogger.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {

    List<Post> findByUser(User user);

    @Query("SELECT p FROM Post p WHERE CONCAT(p.title,' ', p.content) LIKE %?1%")
    List<Post> searchByKeyword(String keyword);

    List<Post> findByCategory(Category category);
}
