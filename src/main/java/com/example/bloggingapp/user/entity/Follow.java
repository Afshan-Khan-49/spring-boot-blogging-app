package com.example.bloggingapp.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Builder
@Table(name = "user_follow")
//@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @EmbeddedId
    private FollowId id;

    @MapsId("fromId")
    @JoinColumn(name = "from_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User from;

    @MapsId("toId")
    @JoinColumn(name = "to_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    public Follow(User from, User to) {
        this.id = new FollowId(from.getId(), to.getId());
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return id.equals(follow.id) && from.equals(follow.from) && to.equals(follow.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.from, this.to);
    }
}
