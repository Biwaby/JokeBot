package com.biwaby.projects.jokebot.service.interfaces;

import com.biwaby.projects.jokebot.model.Joke;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface JokeService {

    Joke addJoke(Joke joke);

    Page<Joke> getAllJokes(int page);

    Joke getJokeById(Long id);

    Joke getRandomJoke(Long userId);

    List<Joke> getTopFive();

    void deleteJoke(Long id);

    Joke editJoke(Long id, Joke joke);
}
