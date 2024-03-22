package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Chapter;
import com.example.demo.model.request.CreateChapterRequest;
import com.example.demo.model.request.UpdateChapterRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.service.ChapterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chapter")
@CrossOrigin(origins = "*",maxAge = 36000)
public class ChapterController {
    
    @Autowired
    private ChapterService chapterService;

    @PostMapping("/create")
    public ResponseEntity<?> createComic(@Valid @RequestBody CreateChapterRequest request){
        Chapter chapter = chapterService.createChapter(request);
        return ResponseEntity.ok(chapter);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateComic(@Valid @RequestBody UpdateChapterRequest request){
        Chapter chapter = chapterService.updateChapter(request);
        return ResponseEntity.ok(chapter);
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<?> enabled(@PathVariable long id){
        chapterService.enableChapter(id);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }

}
