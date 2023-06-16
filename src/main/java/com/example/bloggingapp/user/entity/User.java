package com.example.bloggingapp.user.entity;

import com.example.bloggingapp.post.dto.PostResponseDto;
import com.example.bloggingapp.post.entity.FavoritePost;
import com.example.bloggingapp.post.entity.Post;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Entity
@Getter
@Builder
@Table(name = "users")
//@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 30, nullable = false)
    private String firstName;
    private String lastName;
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    private String loginType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "from", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> following = new HashSet<>();

    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "to", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> follower = new HashSet<>();

    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FavoritePost> favoritePosts = new HashSet<>();

    public boolean isFavoritePost(@NotNull Post post) {
        FavoritePost favoritePost = new FavoritePost(this,post);
        return this.getFavoritePosts().stream().anyMatch(favoritePost::equals);
    }

    public void markFavorite(Post post) {
        if(!this.isFavoritePost(post)) {
            FavoritePost favoritePost = new FavoritePost(this, post);
            this.addFavoritePost(favoritePost);
            post.addFavoriteUser(favoritePost);
        }
    }

    private void addFavoritePost(FavoritePost favoritePost) {
        this.getFavoritePosts().add(favoritePost);
    }

    public void markUnFavorite(Post post) {
        if(this.isFavoritePost(post)) {
            FavoritePost favoritePost = new FavoritePost(this,post);
            this.removeFavoritePost(favoritePost);
            post.removeFavoriteUser(favoritePost);
        }
    }

    private void removeFavoritePost(FavoritePost favoritePost) {
        this.getFavoritePosts().remove(favoritePost);
    }
}
