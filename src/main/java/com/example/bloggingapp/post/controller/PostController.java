package com.example.bloggingapp.post.controller;

import com.example.bloggingapp.post.dto.*;
import com.example.bloggingapp.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto) {

        System.out.println(createPostRequestDto.getTagList());

        PostResponseDto post = postService.createPost(createPostRequestDto);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ListPostResponse getPosts(@RequestParam(value = "author", required = false) String authorEmail,
                                     @RequestParam(value = "tags", required = false) List<String> tags,
                                     @RequestParam(value = "isFavorite", required = false) boolean isFavorite,
                                     Pageable pageable) {
        return postService.getPosts(authorEmail, tags, isFavorite, pageable);

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
        return postService.addComment(title, createCommentRequest.getContent());
    }

    @GetMapping("/{title}/comments")
    public Page<CommentResponse> getComments(@PathVariable String title, Pageable pageable) {
        return postService.getComments(title, pageable);
    }

    @DeleteMapping("/{title}/comments/{commentId}")
    public void deleteComment(@PathVariable String title, @PathVariable int commentId) {
        postService.deleteComment(commentId);
    }

    @PostMapping("/{title}/favorite")
    public PostResponseDto favoritePost(@PathVariable String title) {
        return postService.favoritePost(title);
    }

    @DeleteMapping("/{title}/favorite")
    public PostResponseDto unfavorite(@PathVariable String title) {
        return postService.unfavorite(title);
    }

}
