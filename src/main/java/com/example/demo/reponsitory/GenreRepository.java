package com.example.demo.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {

    @Query(value = "SELECT * FROM genre WHERE LOWER(name) = LOWER(:name)", nativeQuery = true)
    Genre findGenreByName(String name);
    
    @Query(value = "Select * from genre WHERE enable = 1", nativeQuery = true)
    List<Genre> findALLByEnabled();
}
