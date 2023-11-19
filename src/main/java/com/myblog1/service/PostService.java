
package com.myblog1.service;

import com.myblog1.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(long id);

    void updatePost(long id);

    PostDto getPostById(long id);

    List<PostDto> getPost(int pageNo, int pageSize, String sortBy);
}
