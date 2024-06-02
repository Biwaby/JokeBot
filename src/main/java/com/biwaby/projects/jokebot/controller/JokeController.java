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
    public ResponseEntity<Joke> addJoke(@RequestBody Joke joke) {
        return ResponseEntity.ok(jokeService.addJoke(joke));
    }

    // GET /jokes?page={num_page} - выдает все шутки с пагинацией
    @GetMapping
    public ResponseEntity<Page<Joke>> getAllJokes(@RequestParam int page) {
        return ResponseEntity.ok(jokeService.getAllJokes(page));
    }

    // GET /jokes/{id} - выдает шутку с данным id или же 404 если ничего не нашлось
    @GetMapping("/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable Long id) {
        return ResponseEntity.ok(jokeService.getJokeById(id));
    }

    // GET /jokes/topFiveJokes - выдает список топ-5 шуток по количество просмотров пользователей
    @GetMapping("/topFiveJokes")
    public ResponseEntity<List<Joke>> getTopFive() {
        return ResponseEntity.ok(jokeService.getTopFive());
    }

    // DELETE /jokes/{id} - удаляет шутку с данным ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoke(@PathVariable Long id) {
        jokeService.deleteJoke(id);
        return ResponseEntity.ok().build();
    }

    // PUT /jokes/{id} - изменение шутки с данным ID
    // Устанавливает дату обновления.
    @PutMapping("/{id}")
    public ResponseEntity<Joke> editJoke(@PathVariable Long id, @RequestBody Joke joke) {
        return ResponseEntity.ok(jokeService.editJoke(id, joke));
    }
}
