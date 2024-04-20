package com.biwaby.projects.jokebot.repository;

import com.biwaby.projects.jokebot.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface JokesRepository extends PagingAndSortingRepository<Joke, Long>, JpaRepository<Joke, Long> { }
