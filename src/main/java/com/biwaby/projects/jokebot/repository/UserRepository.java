package com.biwaby.projects.jokebot.repository;

import com.biwaby.projects.jokebot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
