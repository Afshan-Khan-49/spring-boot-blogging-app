package com.example.bloggingapp.post.entity;

import com.example.bloggingapp.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Builder
@Table(name = "post_tag")
//@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class PostTag {
    @EmbeddedId
    private PostTagId id;

    @MapsId("postId")
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;


    public PostTag(Post post, Tag tag) {
        this.id = new PostTagId(post.getId(), tag.getId());
        this.post = post;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTag postTag = (PostTag) o;
        return id.equals(postTag.id) && post.equals(postTag.post) && tag.equals(postTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post, tag);
    }
}
