package com.example.bloggingapp.user.entity;

import com.example.bloggingapp.post.entity.FavoritePost;
import com.example.bloggingapp.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        FavoritePost favoritePost = new FavoritePost(this, post);
        return this.getFavoritePosts().stream().anyMatch(favoritePost::equals);
    }

    public void markFavorite(Post post) {
        if (!this.isFavoritePost(post)) {
            FavoritePost favoritePost = new FavoritePost(this, post);
            this.addFavoritePost(favoritePost);
            post.addFavoriteUser(favoritePost);
        }
    }

    private void addFavoritePost(FavoritePost favoritePost) {
        this.getFavoritePosts().add(favoritePost);
    }

    public void markUnFavorite(Post post) {
        if (this.isFavoritePost(post)) {
            FavoritePost favoritePost = new FavoritePost(this, post);
            this.removeFavoritePost(favoritePost);
            post.removeFavoriteUser(favoritePost);
        }
    }

    private void removeFavoritePost(FavoritePost favoritePost) {
        this.getFavoritePosts().remove(favoritePost);
    }

    public boolean isAlreadyFollowing(User targetUser) {
        Follow follow = new Follow(this, targetUser);
        return this.getFollowing().stream().anyMatch(follow::equals);
    }

    public void follow(User targetUser) {
        if (this.isAlreadyFollowing(targetUser))
            return;
        Follow follow = new Follow(this, targetUser);
        this.addFollowing(follow);
        this.addFollower(follow);
    }

    private void addFollower(Follow follow) {
        follow.getTo().getFollower().add(follow);
    }

    private void addFollowing(Follow follow) {
        this.following.add(follow);
    }


    public void unfollow(User targetUser) {
        if (!this.isAlreadyFollowing(targetUser))
            return;
        Follow follow = new Follow(this, targetUser);
        this.removeFollowing(follow);
        this.removeFollower(follow);
    }

    private void removeFollower(Follow follow) {
        follow.getTo().getFollower().remove(follow);
    }

    private void removeFollowing(Follow follow) {
        this.following.remove(follow);
    }
}
