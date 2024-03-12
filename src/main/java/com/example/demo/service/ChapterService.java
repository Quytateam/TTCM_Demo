package com.example.demo.service;

import com.example.demo.entity.Chapter;
import com.example.demo.model.request.CreateChapterRequest;
import com.example.demo.model.request.UpdateChapterRequest;

public interface ChapterService {
    
    Chapter createChapter(CreateChapterRequest request);

    Chapter updateChapter(UpdateChapterRequest request);

    void enableChapter(long id);

    Chapter getChapter(String comicName, String chapName);
}
