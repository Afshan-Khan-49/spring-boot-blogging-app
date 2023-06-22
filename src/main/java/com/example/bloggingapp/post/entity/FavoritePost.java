package com.example.bloggingapp.post.entity;

import com.example.bloggingapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Builder
@Table(name = "post_favorite")
@AllArgsConstructor
@NoArgsConstructor
public class FavoritePost {
    @EmbeddedId
    private FavoritePostId id;

    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId("postId")
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public FavoritePost(User user, Post post) {
        this.id = new FavoritePostId(user.getId(), post.getId());
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritePost that = (FavoritePost) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, post);
    }
}
