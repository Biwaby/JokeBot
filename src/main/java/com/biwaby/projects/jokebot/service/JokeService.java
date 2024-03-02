package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.Joke;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface JokeService {

    void addJoke(Joke joke);

    List<Joke> getAllJokes();

    Optional<Joke> getJokeById(Long id);

    boolean deleteJoke(Long id);

    boolean editJoke(Long id, Joke joke);
}
