package com.biwaby.projects.jokebot.service.implementations;

import com.biwaby.projects.jokebot.exceptions.IncorrectJokeException;
import com.biwaby.projects.jokebot.exceptions.JokeNotFoundException;
import com.biwaby.projects.jokebot.model.Joke;
import com.biwaby.projects.jokebot.model.JokeCallLog;
import com.biwaby.projects.jokebot.repository.JokesRepository;
import com.biwaby.projects.jokebot.service.interfaces.JokeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class JokeServiceImpl implements JokeService {

    private final JokesRepository jokesRepository;

    @Override
    public Joke addJoke(Joke joke) {
        if (joke.getJoke().isEmpty() || joke.getJoke() == null) {
            throw new IncorrectJokeException();
        }

        joke.setCreationDate(new Date());
        joke.setUpdatingDate(new Date());
        return jokesRepository.save(joke);
    }

    @Override
    public Page<Joke> getAllJokes(int page) {
        return jokesRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public Joke getJokeById(Long id) {
        Joke joke = jokesRepository.findById(id).orElseThrow(JokeNotFoundException::new);
        joke.getJokeCallHistory().add(new JokeCallLog(null, joke, null, new Date()));
        jokesRepository.save(joke);
        return joke;
    }

    @Transactional
    @Override
    public Joke getRandomJoke(Long userId) {
        Joke radndomJoke = jokesRepository.getRandomJoke();
        radndomJoke.getJokeCallHistory().add(new JokeCallLog(null, radndomJoke, userId, new Date()));
        return jokesRepository.save(radndomJoke);
    }

    @Transactional
    @Override
    public List<Joke> getTopFive() {
        return jokesRepository.findTopFiveJokes();
    }

    @Override
    public void deleteJoke(Long id) {
        Joke joke = jokesRepository.findById(id).orElseThrow(JokeNotFoundException::new);
        jokesRepository.delete(joke);
    }

    @Override
    public Joke editJoke(Long id, Joke joke) {
        Joke newJoke = jokesRepository.findById(id).orElseThrow(JokeNotFoundException::new);
        newJoke.setJoke(joke.getJoke());
        newJoke.setUpdatingDate(new Date());
        jokesRepository.save(newJoke);
        return newJoke;
    }
}
