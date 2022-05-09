package com.unborn.blogger.services;

import com.unborn.blogger.datatransferobject.PostDto;
import com.unborn.blogger.datatransferobject.PostResponse;
import com.unborn.blogger.entities.Category;
import com.unborn.blogger.entities.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Long categoryId, Long userId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);

    PostDto getPost(Long id);

    void deletePost(Long id);

    PostDto updatePost( Long id,PostDto postDto);

    List<PostDto> getAllPostsByCategory(Long category_id);

    List<PostDto> getAllPostsByUser(Long user_id);

    List<PostDto> searchKeyword(String keyword);

    PostDto updateImage(Long id, String fileName);
}
