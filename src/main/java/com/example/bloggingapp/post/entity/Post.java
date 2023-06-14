package com.example.bloggingapp.post.entity;

import com.example.bloggingapp.tag.entity.Tag;
import com.example.bloggingapp.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Entity
@Getter
@Setter
//@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(length = 50, nullable = false)
    private String description;

    @Column(length = 50, unique = true, nullable = false)
    private String title;

    @Column(length = 1_000, nullable = false, columnDefinition = "varchar(255) default 'No description provided'")
    private String content = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FavoritePost> favoriteUsers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostTag> includeTags = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private Post(Integer id, User author, String description, String title, String content) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.title = title;
        this.content = content;
        this.favoriteUsers = new HashSet<>();
        this.includeTags = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addTag(@NotNull Tag newTag) {
        PostTag postTag = new PostTag(this, newTag);

        if(this.includeTags.stream().anyMatch(postTag::equals))
            return;

        this.includeTags.add(postTag);
    }

    public void addTags(Set<Tag> tags) {
        if(!tags.isEmpty())
            tags.forEach(this::addTag);
    }

    public boolean writtenByCurrentUser(String currentUserEmail) {
        return this.author.getEmail().equals(currentUserEmail);
    }

    public void addFavoriteUser(FavoritePost favoritePost) {
        this.getFavoriteUsers().add(favoritePost);
    }
}


