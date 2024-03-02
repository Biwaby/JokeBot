package com.biwaby.projects.jokebot.controller;

import com.biwaby.projects.jokebot.model.Joke;
import com.biwaby.projects.jokebot.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jokes")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService jokeService;

    // POST /jokes - принимает JSON шутки в Body. Устанавливает дату создания.
    @PostMapping
    ResponseEntity<Void> addJoke(@RequestBody Joke joke) {
        jokeService.addJoke(joke);
        return ResponseEntity.ok().build();
    }

    // GET /jokes - не принимает никаких параметров, выдает список шуток по модели
    @GetMapping
    ResponseEntity<List<Joke>> getJokes() {
        return ResponseEntity.ok(jokeService.getAllJokes());
    }

    // GET /jokes/id - принимает id как параметр пути, выдает шутку с данным id или же 404 если ничего не нашлось
    @GetMapping("/{id}")
    ResponseEntity<Joke> getJokeById(@PathVariable Long id) {
        return jokeService.getJokeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    // PUT /jokes/id - принимает id как параметр пути, изменяет шутку с данным id. Поле id в Body - игнорируется.
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
