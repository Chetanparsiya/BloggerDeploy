package com.unborn.blogger.controller;


import com.unborn.blogger.config.AppConstants;
import com.unborn.blogger.datatransferobject.ApiResponse;
import com.unborn.blogger.datatransferobject.FileResponse;
import com.unborn.blogger.datatransferobject.PostDto;
import com.unborn.blogger.datatransferobject.PostResponse;
import com.unborn.blogger.entities.Post;
import com.unborn.blogger.services.FileService;
import com.unborn.blogger.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    String path;

    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId, @PathVariable Long categoryId){
        PostDto createdPost = postService.createPost(postDto, categoryId,userId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getPosts(@RequestParam (value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                 @RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                 @RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){

        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        PostDto postDto = postService.getPost(id);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId){
        List<PostDto> postsDto = postService.getAllPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postsDto, HttpStatus.OK);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> searchByKeyword(@RequestParam(value = "search", defaultValue = AppConstants.SEARCH_VALUE,required = false) String keyword){
        List<PostDto> postDtos = postService.searchKeyword(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
    }
    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
        List<PostDto> postsDto = postService.getAllPostsByCategory(userId);
        return new ResponseEntity<List<PostDto>>(postsDto, HttpStatus.OK);
    }

    @PutMapping("posts/{id}")
    public ResponseEntity<PostDto> updatePosts(@PathVariable Long id, @RequestBody PostDto postDto){
        PostDto updatedpostDto = postService.updatePost(id, postDto);
        return new ResponseEntity<PostDto>(updatedpostDto, HttpStatus.OK);
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true), HttpStatus.OK);
    }

    @PostMapping(value = "posts/image/upload/{id}")
    public ResponseEntity<FileResponse>  uploadImage(@PathVariable Long id, @RequestParam(value="image") MultipartFile file) throws IOException {
        String fileName = fileService.uploadImage(path,file);
        PostDto postDto = postService.updateImage(id,fileName);
        return new ResponseEntity<FileResponse>(new FileResponse(postDto,fileName,"Image Upladed Succefully"),HttpStatus.OK);
    }

    @GetMapping(value = "posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void  downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
