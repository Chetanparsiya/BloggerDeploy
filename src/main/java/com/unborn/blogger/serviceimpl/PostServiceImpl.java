package com.unborn.blogger.serviceimpl;

import com.unborn.blogger.datatransferobject.PostDto;
import com.unborn.blogger.datatransferobject.PostResponse;
import com.unborn.blogger.entities.Category;
import com.unborn.blogger.entities.Post;
import com.unborn.blogger.entities.User;
import com.unborn.blogger.exceptions.ResourceNotFoundException;
import com.unborn.blogger.repositories.CategoryRepo;
import com.unborn.blogger.repositories.PostRepo;
import com.unborn.blogger.repositories.UserRepo;
import com.unborn.blogger.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService  {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Long categoryId, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Categoru Id", categoryId));
        Post post = postDtoToPost(postDto);
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post createdPost = postRepo.save(post);
        return postToPostDto(createdPost);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
           sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePosts = postRepo.findAll(p);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postsDto = posts.stream().map(post -> postToPostDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postsDto);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize( pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLastPage(pagePosts.isLast());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        return postResponse;
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
        return postToPostDto(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(Long id,PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        post.setTitle(postDto.getTitle());
        Post updatedPost = postRepo.save(post);
        return postToPostDto(updatedPost);
    }

    @Override
    public List<PostDto> getAllPostsByCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<Post> posts = postRepo.findByCategory(category);
        List<PostDto>postsDto = posts.stream().map(post -> postToPostDto(post)).collect(Collectors.toList());
        return postsDto;
    }

    @Override
    public List<PostDto> getAllPostsByUser(Long userId){
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto>postsDto = posts.stream().map(post -> postToPostDto(post)).collect(Collectors.toList());
        return postsDto;
    }

    @Override
    public List<PostDto> searchKeyword(String keyword) {
        List<Post>posts = postRepo.searchByKeyword("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream().map(post-> postToPostDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto updateImage(Long id, String fileName) {
       Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", id));
        post.setImageUrl(fileName);
        Post  updatedPost = postRepo.save(post);
        return postToPostDto(updatedPost);
    }

    public Post postDtoToPost(PostDto postDto){
      return modelMapper.map(postDto, Post.class);
    }

    public PostDto postToPostDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }
}
