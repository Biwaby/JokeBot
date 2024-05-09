package com.biwaby.projects.jokebot.repository;

import com.biwaby.projects.jokebot.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JokesRepository extends PagingAndSortingRepository<Joke, Long>, JpaRepository<Joke, Long> {

    @Query(value = "SELECT j.id, j.joke, j.creation_date, j.update_date, COUNT(jc.joke_id) AS calls_count FROM jokes j JOIN jokes_call_log jc ON j.id = jc.joke_id GROUP BY j.id ORDER BY calls_count DESC LIMIT 5", nativeQuery = true)
    List<Joke> findTopFiveJokes();
}
