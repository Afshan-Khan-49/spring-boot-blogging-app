package com.example.bloggingapp.post.mapper;

import com.example.bloggingapp.post.dto.PostResponseDto;
import com.example.bloggingapp.post.entity.Post;
import com.example.bloggingapp.post.entity.PostTag;

import java.util.Set;

public interface PostMapper {
    PostResponseDto postToPostResponseDto(Post post);

    Set<String> postTagToTagNames(Set<PostTag> postTags);
}
