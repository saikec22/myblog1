package com.myblog1.service.impl;

import com.myblog1.entity.Post;
import com.myblog1.exception.ResponseNotFound;
import com.myblog1.payload.PostDto;
import com.myblog1.repository.PostRepository;
import com.myblog1.service.PostService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post=mapToEntity(postDto);//this.maptoEnity(postDto)

        Post savedPost = postRepo.save(post);

        PostDto dto = mapToDto(savedPost);//this.mapToDto(savedPost);

        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepo.deleteById(id);
    }

    @Override
    public void updatePost(long id) {
        Post post=postRepo.findById(id).orElseThrow(
                ()->new ResponseNotFound("post not found"+id)
        );
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()-> new ResponseNotFound("post not found:"+id)
        );
        PostDto dto =mapToDto(post);

        return dto;
    }

    @Override
    public List<PostDto> getPost(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        Page<Post> pagePosts = postRepo.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos=posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    PostDto mapToDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

    Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
