package com.biwaby.projects.jokebot.repository;


import com.biwaby.projects.jokebot.model.PhotoMemes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoMemesRepository extends JpaRepository<PhotoMemes, Long> {
}
