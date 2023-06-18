package com.example.bloggingapp.tag.controller;

import com.example.bloggingapp.tag.entity.Tag;
import com.example.bloggingapp.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping
    Page<Tag> getTags(Pageable pageRequest) {
        return tagService.getTags(pageRequest);
    }
}
