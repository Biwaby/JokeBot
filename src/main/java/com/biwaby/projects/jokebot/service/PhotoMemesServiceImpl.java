package com.biwaby.projects.jokebot.service;

import com.biwaby.projects.jokebot.model.PhotoMemes;
import com.biwaby.projects.jokebot.repository.PhotoMemesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhotoMemesServiceImpl implements PhotoMemesService{

    private final PhotoMemesRepository photoMemesRepository;

    @Override
    public void addPhotoMeme(PhotoMemes photoMeme) {
        photoMeme.setCreationDate(LocalDate.now());
        photoMeme.setUpdatingDate(LocalDate.now());
        photoMemesRepository.save(photoMeme);
    }

    @Override
    public List<PhotoMemes> getAllPhotoMemes() {
        return photoMemesRepository.findAll();
    }

    @Override
    public Optional<PhotoMemes> getPhotoMemeById(Long id) {
        return photoMemesRepository.findById(id);
    }

    @Override
    public boolean deletePhotoMeme(Long id) {
        Optional<PhotoMemes> photoMeme = photoMemesRepository.findById(id);
        if (photoMeme.isPresent()) {
            photoMemesRepository.delete(photoMeme.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean editPhotoMeme(Long id, PhotoMemes photoMeme) {
        Optional<PhotoMemes> editablePhotoMeme = photoMemesRepository.findById(id);
        if (editablePhotoMeme.isPresent()) {
            PhotoMemes newPhotoMeme = editablePhotoMeme.get();
            newPhotoMeme.setPhotoPath(photoMeme.getPhotoPath());
            newPhotoMeme.setUpdatingDate(LocalDate.now());
            photoMemesRepository.save(newPhotoMeme);
            return true;
        }
        else {
            return false;
        }
    }
}
