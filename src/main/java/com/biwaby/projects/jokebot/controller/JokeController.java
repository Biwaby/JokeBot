package com.biwaby.projects.jokebot.controller;

import com.biwaby.projects.jokebot.model.Joke;
import com.biwaby.projects.jokebot.service.interfaces.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jokes")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService jokeService;

    // POST /jokes - добавляет новую шутку
    @PostMapping
    ResponseEntity<Joke> addJoke(@RequestBody Joke joke) {
        return ResponseEntity.ok(jokeService.addJoke(joke));
    }

    // GET /jokes?page=num_page - выдает все шутки с пагинацией
    @GetMapping
    ResponseEntity<Page<Joke>> getAllJokes(@RequestParam int page) {
        return ResponseEntity.ok(jokeService.getAllJokes(page));
    }

    // GET /jokes/id - выдает шутку с данным id или же 404 если ничего не нашлось
    @GetMapping("/{id}")
    ResponseEntity<Joke> getJokeById(@PathVariable Long id) {
        return jokeService.getJokeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /topFiveJokes - выдает список топ-5 шуток по количество просмотров пользователей
    @GetMapping("/topFiveJokes")
    ResponseEntity<List<Joke>> getTopFive() {
        return ResponseEntity.ok(jokeService.getTopFive());
    }

    // DELETE /jokes/id - удаляет шутку с данным ID
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteJoke(@PathVariable Long id) {
        if (jokeService.deleteJoke(id)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /jokes/id - изменение шутки с данным ID
    // Устанавливает дату обновления.
    @PutMapping("/{id}")
    ResponseEntity<Void> editJoke(@PathVariable Long id, @RequestBody Joke joke) {
        if (jokeService.editJoke(id, joke)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
