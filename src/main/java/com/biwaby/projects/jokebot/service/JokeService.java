package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.Joke;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface JokeService {

    Joke addJoke(Joke joke);

    Page<Joke> getAllJokes(int page);

    Optional<Joke> getJokeById(Long id);

    Joke getRandomJoke(Long userId);

    List<Joke> getTopFive();

    boolean deleteJoke(Long id);

    boolean editJoke(Long id, Joke joke);
}
