package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.Joke;
import com.biwaby.projects.jokebot.repository.JokesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class JokeServiceImpl implements JokeService {

    private final JokesRepository jokesRepository;

    @Override
    public void addJoke(Joke joke) {
        joke.setCreationDate(LocalDate.now());
        joke.setUpdatingDate(LocalDate.now());
        jokesRepository.save(joke);
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokesRepository.findAll();
    }

    @Override
    public Optional<Joke> getJokeById(Long id) {
        return jokesRepository.findById(id);
    }

    @Override
    public boolean deleteJoke(Long id) {
        Optional<Joke> joke = jokesRepository.findById(id);
        if (joke.isPresent()) {
            jokesRepository.delete(joke.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean editJoke(Long id, Joke joke) {
        Optional<Joke> editableJoke = jokesRepository.findById(id);
        if (editableJoke.isPresent()) {
            Joke newJoke = editableJoke.get();
            newJoke.setJoke(joke.getJoke());
            newJoke.setUpdatingDate(LocalDate.now());
            jokesRepository.save(newJoke);
            return true;
        }
        else {
            return false;
        }
    }
}
