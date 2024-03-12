package com.example.demo.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comic;
import com.example.demo.entity.Genre;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.request.CreateGenreRequest;
import com.example.demo.reponsitory.ComicRepository;
import com.example.demo.reponsitory.GenreRepository;
import com.example.demo.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService{

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Override
    public List<Genre> findAll() {
        // TODO Auto-generated method stub
        List<Genre> list = genreRepository.findAll(Sort.by("id").descending());
        return list;
    }

    @Override
    public List<Genre> getListEnabled() {
        // TODO Auto-generated method stub
        List<Genre> list = genreRepository.findALLByEnabled();
        return list;
    }

    @Override
    public Genre createGenre(CreateGenreRequest request){
        Genre existGenre = genreRepository.findGenreByName(request.getName());
        if(existGenre == null || existGenre.getName().isEmpty()){
            // TODO Auto-generated method stub
            Genre genre =  new Genre();
            genre.setName(request.getName().toLowerCase());
            genre.setEnable(0);
            genreRepository.save(genre);
            return genre;
        }else{
            throw new BadRequestException("Thể loại này đã tồn tại");
        }
    }

    @Override
    public Genre updateGenre(long id, CreateGenreRequest request) {
        // TODO Auto-generated method stub
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Found Genre With Id: " + id));
        Genre existGenre = genreRepository.findGenreByName(request.getName());
        if(existGenre == null || existGenre.getName().isEmpty()){
            // TODO Auto-generated method stub
            genre.setName(request.getName().toLowerCase());
            genre.setEnable(0);
            genreRepository.save(genre);
            return genre;
        }else{
            throw new BadRequestException("Thể loại này đã tồn tại");
        }
    }

    @Override
    public void enableGenre(long id) {
        // TODO Auto-generated method stub
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Found Genre With Id: " + id));
        if(genre.getEnable() == 1){
            genre.setEnable(0);
        } else{
            genre.setEnable(1);
        }
        genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(long id) {
        // TODO Auto-generated method stub
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Found Genre With Id: " + id));
        genreRepository.delete(genre);
    }

    @Override
    public List<Comic> getListComicByGenre(String genrename){
        List<Comic> list = comicRepository.getListComicByGenre(genrename);
        return list;
    }

    @Override
    public List<Comic> searchComic(String keyword) {
        // TODO Auto-generated method stub
        List<Comic> list = comicRepository.searchComic(keyword);
        return list;
    }
}
