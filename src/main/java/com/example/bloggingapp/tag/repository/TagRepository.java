package com.example.bloggingapp.tag.repository;

import com.example.bloggingapp.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String postTag);

    Set<Tag> findByNameIn(List<String> tagNames);
}
