package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.cloudinary.Cloudinary;
import com.example.demo.entity.Chapter;
import com.example.demo.entity.Comic;
import com.example.demo.entity.ImageInfo;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateChapterRequest;
import com.example.demo.model.request.UpdateChapterRequest;
import com.example.demo.reponsitory.ChapterRepository;
import com.example.demo.reponsitory.ComicRepository;
import com.example.demo.service.ChapterService;
import com.example.demo.service.ImageService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService{

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ImageService imageService;
    
    // private final Cloudinary cloudinary;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Chapter getChapter(String comicName, String chapName){
        Chapter chapter = chapterRepository.getChapter(comicName, chapName);
        try{
            // String folderPath = comicName.toLowerCase() + "/" + chapName.toLowerCase();
            Map<String, Object> result = imageService.getFile(comicName,chapName);
            List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");
            List<ImageInfo> imageInfoList = new ArrayList<>();
            for (Map<String, Object> resource : resources) {
                String url = (String) resource.get("secure_url");
                String imgName = (String) resource.get("filename");
                
                ImageInfo imageInfo = new ImageInfo(url, imgName);
                imageInfoList.add(imageInfo);
            }
            // Collections.sort(imageInfoList, new Comparator<ImageInfo>() {
            //     @Override
            //     public int compare(ImageInfo imageInfo1, ImageInfo imageInfo2) {
            //         return imageInfo1.getImgName().compareTo(imageInfo2.getImgName());
            //     }
            // });
            chapter.setImageInfo(imageInfoList);
        }catch(Exception ex){
            chapter.setImageInfo(null);
        }
        Comic comic =  comicRepository.findComicByName(comicName);
        comic.setView(comic.getView() + 1);
        comicRepository.save(comic);
        return chapter;
    }

    @Transactional
    public Chapter createChapter(CreateChapterRequest request){
        // Comic comic = entityManager.find(Comic.class, request.getComicId());
        Comic comic = comicRepository.findById(request.getComicId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Comic với id: " + request.getComicId()));
        Chapter chapter = new Chapter();
        // chapter.setComic(comic);
        chapter.setComicId(comic.getId());
        chapter.setComicName(comic.getName());
        chapter.setName(request.getName());
        chapter.setContent(request.getContent());
        chapter.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chapter.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chapter.setEnable(0);
        chapterRepository.save(chapter);
        comic.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(chapter);
        return chapter;
    }

    @Override
    public Chapter updateChapter(UpdateChapterRequest request){
        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Chapter với id: " + request.getChapterId()));
        chapter.setName(request.getName());
        chapter.setContent(request.getContent());
        chapter.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chapterRepository.save(chapter);
        return chapter;
    }

    @Override
    public void enableChapter(long id) {
        Chapter chapter = chapterRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Chapter With Id: " + id));
        if(chapter.getEnable() == 1){
            chapter.setEnable(0);
        } else{
            chapter.setEnable(1);
        }
        chapter.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chapterRepository.save(chapter);
    }
}
