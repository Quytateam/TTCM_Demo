package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Comic;
import com.example.demo.model.request.CreateComicRequest;

public interface ComicService {

    Comic createComic(CreateComicRequest request);

    Comic updateComic(long id, CreateComicRequest request);

    List<Comic> getList();

    List<Comic> getListEnabled();

    Comic getComic(String namevsid);

    void enableComic(long id);

    // Comic updateImage(long id);
}
