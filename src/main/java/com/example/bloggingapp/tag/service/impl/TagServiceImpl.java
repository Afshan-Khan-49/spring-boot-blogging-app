package com.example.bloggingapp.tag.service.impl;

import com.example.bloggingapp.tag.entity.Tag;
import com.example.bloggingapp.tag.repository.TagRepository;
import com.example.bloggingapp.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    @Override
    public Set<Tag> findByTagNames(List<String> tagNames) {
        return tagRepository.findByNameIn(tagNames);
    }

    @Override
    public Optional<Tag> findByName(String tag) {
        return tagRepository.findByName(tag);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
