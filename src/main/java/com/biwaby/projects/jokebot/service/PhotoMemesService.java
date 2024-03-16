package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.PhotoMemes;

import java.util.List;
import java.util.Optional;

public interface PhotoMemesService {
    void addPhotoMeme(PhotoMemes photoMeme);

    List<PhotoMemes> getAllPhotoMemes();

    Optional<PhotoMemes> getPhotoMemeById(Long id);

    boolean deletePhotoMeme(Long id);

    boolean editPhotoMeme(Long id, PhotoMemes photoMeme);
}
