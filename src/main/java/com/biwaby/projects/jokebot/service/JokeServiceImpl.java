package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.Joke;
import com.biwaby.projects.jokebot.model.JokeCallLog;
import com.biwaby.projects.jokebot.repository.JokesRepository;
import com.biwaby.projects.jokebot.utils.NowService;
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
    private final NowService nowService;

    @Override
    public Joke addJoke(Joke joke) {
        //joke.setId(null);
        joke.setCreationDate(nowService.getCurrentDate());
        joke.setUpdatingDate(nowService.getCurrentDate());
        return jokesRepository.saveAndFlush(joke);
    }

    @Override
    public Page<Joke> getAllJokes(int page) {
        return jokesRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public Optional<Joke> getJokeById(Long id) {
        Optional<Joke> optionalJoke = jokesRepository.findById(id);
        if (optionalJoke.isPresent()) {
            Joke currentJoke = optionalJoke.get();
            currentJoke.getJokeCallHistory().add(new JokeCallLog(null, currentJoke, null, nowService.getCurrentDate()));
            jokesRepository.saveAndFlush(currentJoke);
        }
        return jokesRepository.findById(id);
    }

    @Transactional
    @Override
    public Joke getRandomJoke(Long userId) {
        List<Joke> jokeList = jokesRepository.findAll();
        Joke randomJoke = jokeList.get(new Random().nextInt(jokeList.size()));
        randomJoke.getJokeCallHistory().add(new JokeCallLog(null, randomJoke, userId, nowService.getCurrentDate()));
        return jokesRepository.saveAndFlush(randomJoke);
    }

    @Transactional
    @Override
    public List<Joke> getTopFive() {
        List<Joke> jokeList = jokesRepository.findAll();
        jokeList.sort(Comparator.comparing(joke -> joke.getJokeCallHistory().size(), Comparator.reverseOrder()));

        if (jokeList.size() < 5) {
            return jokeList;
        }
        else {
            List<Joke> topList = jokeList.subList(0, 5);
            return topList;
        }
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
            newJoke.setUpdatingDate(nowService.getCurrentDate());
            jokesRepository.save(newJoke);
            return true;
        }
        else {
            return false;
        }
    }
}
