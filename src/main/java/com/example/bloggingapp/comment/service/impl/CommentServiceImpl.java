package com.example.bloggingapp.comment.service.impl;

import com.example.bloggingapp.comment.entity.Comment;
import com.example.bloggingapp.comment.exception.CommentNotFoundException;
import com.example.bloggingapp.comment.mapper.CommentMapper;
import com.example.bloggingapp.comment.repository.CommentRepository;
import com.example.bloggingapp.comment.service.CommentService;
import com.example.bloggingapp.common.util.LoginUtils;
import com.example.bloggingapp.post.dto.CommentResponse;
import com.example.bloggingapp.post.entity.Post;
import com.example.bloggingapp.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponse createComment(Post post, User user, String content) {
        Comment comment = Comment.builder()
                .post(post)
                .author(user)
                .content(content)
                .updatedAt(LocalDateTime.now())
                .build();
        return commentMapper.commentToCommentResponse(commentRepository.save(comment));

    }

    @Override
    public Page<CommentResponse> getComments(Post post, Pageable pageable) {
        log.info("Fetching comments for post: {}",post.getTitle());
        return commentRepository.findByPostOrderByCreatedAtDesc(post,pageable).map(commentMapper::commentToCommentResponse);
    }

    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Could not find comment with id: " + commentId));
        if(!comment.isWrittenByCurrentUser(LoginUtils.getCurrentUserEmail())){
            throw new IllegalArgumentException("Only comments written by you can be deleted");
        }
        commentRepository.delete(comment);

    }
}
