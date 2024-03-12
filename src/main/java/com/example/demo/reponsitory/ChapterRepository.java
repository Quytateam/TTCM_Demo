package com.example.demo.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter,Long>{
    @Query(value = "SELECT * FROM chapter WHERE comic_id = :id AND enable = 1 ORDER BY name DESC", nativeQuery = true)
    List<Chapter> getListChapter(long id);

    @Query(value = "SELECT * FROM chapter WHERE comic_id = :id ORDER BY name DESC", nativeQuery = true)
    List<Chapter> getListAll(long id);

    @Query(value = "SELECT * FROM chapter WHERE LOWER(comic_name) = LOWER(:comicName) AND LOWER(name) = LOWER(:chapName)", nativeQuery = true)
    Chapter getChapter(String comicName, String chapName);
}
