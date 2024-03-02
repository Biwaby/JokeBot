package com.biwaby.projects.jokebot.repository;

import com.biwaby.projects.jokebot.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokesRepository extends JpaRepository<Joke, Long> {
}
