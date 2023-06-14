package com.example.bloggingapp.post.mapper;

import com.example.bloggingapp.post.dto.PostResponseDto;
import com.example.bloggingapp.post.entity.Post;
import com.example.bloggingapp.post.entity.PostTag;
import com.example.bloggingapp.tag.entity.Tag;
import com.example.bloggingapp.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostMapperImpl implements PostMapper {
    @Override
    public PostResponseDto postToPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .updatedAt(post.getUpdatedAt())
                .createdAt(post.getCreatedAt())
                .userResponseDto(UserMapper.MAPPER.userToUserResponse(post.getAuthor()))
                .tags(postTagToTagNames(post.getIncludeTags()))
                .isFavorite(post.getAuthor().isFavoritePost(post))
                .build();
    }

    @Override
    public Set<String> postTagToTagNames(Set<PostTag> postTags) {
        return postTags.stream().map(PostTag::getTag).map(Tag::getName).collect(Collectors.toSet());
    }
}
