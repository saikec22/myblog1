package com.myblog1.controller;

import com.myblog1.payload.PostDto;
import com.myblog1.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/post
    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto){
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//201
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id")long id,@RequestBody PostDto postdto){
        postService.updatePost(id);
        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id")long id){
       PostDto dto= postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);//200
    }

    //   http://localhost:8080/api/post?pageN0=0&pageSize=3
    @GetMapping
    public List<PostDto> getPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value="pageSize", defaultValue = "0", required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id",required = false)String sortBy

    ){
        List<PostDto> postDto=postService.getPost(pageNo,pageSize,sortBy);
        return postDto;
    }

}
