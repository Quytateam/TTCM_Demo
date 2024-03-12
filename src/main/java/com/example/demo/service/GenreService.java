package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Comic;
import com.example.demo.entity.Genre;
import com.example.demo.model.request.CreateGenreRequest;

public interface GenreService {

    List<Genre> findAll();

    List<Genre> getListEnabled();
    
    Genre createGenre(CreateGenreRequest request);

    Genre updateGenre(long id,CreateGenreRequest request);

    void enableGenre(long id);

    void deleteGenre(long id);

    List<Comic> getListComicByGenre(String genrename);

    List<Comic> searchComic(String keyword);
}
