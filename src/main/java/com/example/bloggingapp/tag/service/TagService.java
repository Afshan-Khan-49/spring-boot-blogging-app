package com.example.bloggingapp.tag.service;

import com.example.bloggingapp.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagService {

    Set<Tag> findByTagNames(List<String> tagNames);

    Optional<Tag> findByName(String tag);

    Tag save(Tag tag);

    Page<Tag> getTags(Pageable pageRequest);
}
