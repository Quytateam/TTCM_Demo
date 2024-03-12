package com.example.demo.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Comic;

public interface ComicRepository extends JpaRepository<Comic,Long>{

    @Query(value = "SELECT * FROM comic WHERE enable = 1 ORDER BY updated_at DESC", nativeQuery = true)
    List<Comic> getListEnabled();

    @Query(value = "SELECT * FROM comic WHERE LOWER(name) = LOWER(:name)", nativeQuery = true)
    Comic findComicByName(String name);

    @Query(value = "SELECT c.* FROM comic c JOIN comic_genre cg ON c.id = cg.comic_id JOIN genre g ON cg.genre_id = g.id WHERE LOWER(g.name) = LOWER(:genrename) and c.enable = 1 ORDER BY c.updated_at DESC", nativeQuery = true)
    List<Comic> getListComicByGenre(@Param("genrename") String genrename);

    @Query(value = "SELECT * FROM comic WHERE LOWER(name) LIKE %:keyword% and enable = 1 ORDER BY updated_at DESC", nativeQuery = true)
    List<Comic> searchComic(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM comic WHERE id = :id AND enable = 1", nativeQuery = true)
    Comic getComic(long id);
}
