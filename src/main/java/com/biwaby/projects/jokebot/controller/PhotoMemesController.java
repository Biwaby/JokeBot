package com.biwaby.projects.jokebot.controller;

import com.biwaby.projects.jokebot.model.PhotoMemes;
import com.biwaby.projects.jokebot.service.PhotoMemesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photoMemes")
@RequiredArgsConstructor
public class PhotoMemesController {

    private final PhotoMemesService photoMemesService;

    // POST /photoMemes
    @PostMapping
    ResponseEntity<Void> addPhotoMeme(@RequestBody PhotoMemes photoMeme) {
        photoMemesService.addPhotoMeme(photoMeme);
        return ResponseEntity.ok().build();
    }

    // GET /photoMemes
    @GetMapping
    ResponseEntity<List<PhotoMemes>> getPhotoMemes() {
        return ResponseEntity.ok(photoMemesService.getAllPhotoMemes());
    }

    // GET /photoMemes/id
    @GetMapping("/{id}")
    ResponseEntity<PhotoMemes> getPhotoMemeById(@PathVariable Long id) {
        return photoMemesService.getPhotoMemeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /photoMemes/id
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePhotoMeme(@PathVariable Long id) {
        if (photoMemesService.deletePhotoMeme(id)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /photoMemes/id
    @PutMapping("/{id}")
    ResponseEntity<Void> editPhotoMeme(@PathVariable Long id, @RequestBody PhotoMemes photoMeme) {
        if (photoMemesService.editPhotoMeme(id, photoMeme)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
