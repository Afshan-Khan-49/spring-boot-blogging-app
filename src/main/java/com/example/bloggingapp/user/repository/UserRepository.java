package com.example.bloggingapp.user.repository;

import com.example.bloggingapp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("Select u from User u where u.id in (Select f.from from Follow f where f.to.email = :email)")
    Page<User> getFollowers(String email, Pageable pageable);

    @Query("Select u from User u where u.id in (Select f.to from Follow f where f.from.email = :email)")
    Page<User> getFollowing(String email, Pageable pageRequest);
}
