package com.example.bloggingapp.post.controller;

import com.example.bloggingapp.post.dto.*;
import com.example.bloggingapp.post.mapper.PostMapper;
import com.example.bloggingapp.post.repository.PostRepository;
import com.example.bloggingapp.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    private final PostMapper postMapper;
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto) {

        System.out.println(createPostRequestDto.getTagList());

        PostResponseDto post = postService.createPost(createPostRequestDto);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public Page<PostResponseDto> getPosts(@RequestParam(value = "author", required = false) String authorName ,Pageable pageable) {
        log.info("Getting posts by user");
        return postRepository.findByAuthor_Email(authorName,pageable).map(postMapper::postToPostResponseDto);

    }

    @GetMapping("/{title}")
    public PostResponseDto getPost(@PathVariable String title) {
        return postService.getPostByTitle(title);
    }

    @PutMapping("/{title}")
    public PostResponseDto updatePost(@PathVariable String title, @RequestBody UpdatePostRequestDto updatePostRequestDto) {
        return postService.updatePost(title, updatePostRequestDto);
    }

    @DeleteMapping("/{title}")
    public void deletePost(@PathVariable String title) {
        postService.deletePost(title);
    }

    @PostMapping("/{title}/comments")
    public CommentResponse createComment(@PathVariable String title, @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        return postService.addComment(title,createCommentRequest.getContent());
    }

    @GetMapping("/{title}/comments")
    public Page<CommentResponse> getComments(@PathVariable String title, Pageable pageable) {
        return postService.getComments(title,pageable);
    }

    @DeleteMapping("/{title}/comments/{commentId}")
    public void deleteComment(@PathVariable String title, @PathVariable int commentId) {
        postService.deleteComment(commentId);
    }

    @PostMapping("/{title}/favorite")
    public PostResponseDto favoritePost(@PathVariable String title) {
        return postService.favoritePost(title);
    }

}