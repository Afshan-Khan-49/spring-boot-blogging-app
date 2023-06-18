package com.example.bloggingapp.post.dto;

import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Setter
public class ListPostResponse extends PageImpl<PostResponseDto> {

    public ListPostResponse(List<PostResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
