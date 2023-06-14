package com.example.bloggingapp.user.repository;

import com.example.bloggingapp.user.entity.Follow;
import com.example.bloggingapp.user.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,FollowId> {
}
