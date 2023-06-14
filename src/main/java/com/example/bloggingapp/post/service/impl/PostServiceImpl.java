package com.example.bloggingapp.post.service.impl;

import com.example.bloggingapp.comment.service.CommentService;
import com.example.bloggingapp.common.util.LoginUtils;
import com.example.bloggingapp.post.dto.CommentResponse;
import com.example.bloggingapp.post.dto.CreatePostRequestDto;
import com.example.bloggingapp.post.dto.PostResponseDto;
import com.example.bloggingapp.post.dto.UpdatePostRequestDto;
import com.example.bloggingapp.post.entity.Post;
import com.example.bloggingapp.post.exception.PostNotFoundException;
import com.example.bloggingapp.post.mapper.PostMapper;
import com.example.bloggingapp.post.repository.PostRepository;
import com.example.bloggingapp.post.service.PostService;
import com.example.bloggingapp.tag.entity.Tag;
import com.example.bloggingapp.tag.service.TagService;
import com.example.bloggingapp.user.entity.User;
import com.example.bloggingapp.user.exception.UserNotFoundException;
import com.example.bloggingapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    private final TagService tagService;
    private final CommentService commentService;

    @Override
    public PostResponseDto createPost(CreatePostRequestDto createPostRequestDto) {
        Optional<User> user = userRepository.findByEmail(createPostRequestDto.getUserEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Could not find user with emailId : " + createPostRequestDto.getUserEmail());
        }

        if (postRepository.existsByTitle(createPostRequestDto.getTitle())) {
            throw new IllegalArgumentException("Tile " + createPostRequestDto.getTitle() + " is not available");
        }

        Post post = Post.builder()
                .author(user.get())
                .content(createPostRequestDto.getBody())
                .description(createPostRequestDto.getDescription())
                .title(createPostRequestDto.getTitle())
                .build();

        if (createPostRequestDto.getTagList() != null) {
            for (String tag : createPostRequestDto.getTagList()) {
                Optional<Tag> optionalTag = tagService.findByName(tag);
                Tag newTag = optionalTag.orElseGet(() -> tagService.save(new Tag(tag)));
                post.addTag(newTag);
            }
        }

        Post savedPost = postRepository.save(post);
        return postMapper.postToPostResponseDto(savedPost);

    }

    @Override
    public PostResponseDto getPostByTitle(String title) {
        Post post = checkIfPostExists(title);
        return postMapper.postToPostResponseDto(post);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(String title, UpdatePostRequestDto updatePostRequestDto) {
        Post post = checkIfPostExists(title);

        if (!post.writtenByCurrentUser(LoginUtils.getCurrentUserEmail()))
            throw new IllegalArgumentException("You cannot edit other user's post");

        post.setTitle(updatePostRequestDto.getTitle());
        post.setDescription(updatePostRequestDto.getDescription());
        post.setContent(updatePostRequestDto.getBody());
        if (!CollectionUtils.isEmpty(updatePostRequestDto.getTags())) {
            List<String> tags = updatePostRequestDto.getTags();
            Set<Tag> byTagNames = tagService.findByTagNames(tags);
            Set<String> existingTagNames = byTagNames.stream().map(Tag::getName).collect(Collectors.toSet());
            tags.forEach(tag -> {
                if (!existingTagNames.contains(tag))
                    byTagNames.add(new Tag(tag));
            });
            post.addTags(byTagNames);


        }

        return postMapper.postToPostResponseDto(post);
    }

    @Override
    @Transactional
    public void deletePost(String title) {
        Post post = checkIfPostExists(title);

        if (!post.writtenByCurrentUser(LoginUtils.getCurrentUserEmail()))
            throw new IllegalArgumentException("You cannot delete other user's post");

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public CommentResponse addComment(String title, String content) {
        Post post = checkIfPostExists(title);
        Optional<User> user = userRepository.findByEmail(LoginUtils.getCurrentUserEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Could not find user with emailId : " + LoginUtils.getCurrentUserEmail());
        }
        return commentService.createComment(post, user.get(), content);

    }

    @Override
    public Page<CommentResponse> getComments(String title, Pageable pageable) {
        Post post = checkIfPostExists(title);
        return commentService.getComments(post,pageable);
    }

    @Override
    public void deleteComment(int commentId) {
        commentService.deleteComment(commentId);
    }

    @Override
    @Transactional
    public PostResponseDto favoritePost(String title) {
        Post post = checkIfPostExists(title);
        User user = userRepository.findByEmail(LoginUtils.getCurrentUserEmail()).orElseThrow(() ->
                new UserNotFoundException("Could not find user with emailId : " + LoginUtils.getCurrentUserEmail()));

        user.markFavorite(post);
        return postMapper.postToPostResponseDto(post);
    }

    private Post checkIfPostExists(String title) {
        return postRepository.findByTitle(title).orElseThrow(() -> new PostNotFoundException("Could not find post with title : " + title));
    }


}
