package com.example.demo.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    
    @Query(value = "SELECT * FROM comments WHERE comic_id = :id ORDER BY created_at DESC", nativeQuery = true)
    List<Comment> getByComic(long id);
}
