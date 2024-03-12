package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

// import com.cloudinary.Cloudinary;
import com.example.demo.entity.Chapter;
import com.example.demo.entity.Comic;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Genre;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateComicRequest;
import com.example.demo.reponsitory.ChapterRepository;
import com.example.demo.reponsitory.ComicRepository;
import com.example.demo.reponsitory.CommentRepository;
import com.example.demo.reponsitory.GenreRepository;
import com.example.demo.service.ComicService;
import com.example.demo.service.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComicServiceImpl implements ComicService{
    
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentRepository commentRepository;
    
    // private final Cloudinary cloudinary;

    @Override
    public Comic getComic(String namevsid) {
        String[] parts = namevsid.split("-");
        String lastPart = parts[parts.length - 1];
        long id = Long.parseLong(lastPart);
        // TODO Auto-generated method stub
        Comic comic = comicRepository.getComic(id);
        if(comic.getImage() != null){
            try{
                Map<String, Object> result = imageService.getFile(comic.getImage(),"");
                List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");
                // comic.setImageInfo(result == null || resources.isEmpty() ? null : resources.get(resources.size() - 1));

                String imageUrl = (String) resources.get(resources.size() - 1).get("url");
                comic.setImageLink(imageUrl);
            }catch(Exception ex){
                comic.setImageLink(null);
            }
        }
        List<Chapter> chapters = chapterRepository.getListChapter(id);
        List<Comment> comment = commentRepository.getByComic(id);
        comic.setChapters(chapters.isEmpty() ? null : chapters);
        comic.setComments(comment.isEmpty() ? null : comment );
        return comic;
    }

    @Override
    public List<Comic> getList() {
        // TODO Auto-generated method stub
        List<Comic> comics = comicRepository.findAll(Sort.by("updatedAt").descending());
        comics.forEach(comic -> {
            if(comic.getImage() != null){
                try{
                    Map<String, Object> result = imageService.getFile(comic.getImage(),"");
                    List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");
                    // comic.setImageInfo(result == null || resources.isEmpty() ? null : resources.get(resources.size() - 1));

                    String imageUrl = (String) resources.get(resources.size() - 1).get("url");
                    comic.setImageLink(imageUrl);
                }catch(Exception ex){
                    comic.setImageLink(null);
                }
            }
        });
        return comics;
    }

    @Override
    public List<Comic> getListEnabled() {
        // TODO Auto-generated method stub
        List<Comic> list = comicRepository.getListEnabled();
        list.forEach(comic -> {
            if(comic.getImage() != null){
                try{
                    Map<String, Object> result = imageService.getFile(comic.getImage(),"");
                    List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");
                    // comic.setImageInfo(result == null || resources.isEmpty() ? null : resources.get(resources.size() - 1));

                    String imageUrl = (String) resources.get(resources.size() - 1).get("url");
                    comic.setImageLink(imageUrl);
                }catch(Exception ex){
                    comic.setImageLink(null);
                }
            }
        });
        return list;
    }

    @Override
    public Comic createComic(CreateComicRequest request){
        Comic existComic = comicRepository.findComicByName(request.getName());
        if(existComic == null || existComic.getName().isEmpty()){
            try{
                Comic comic = new Comic();
                comic.setName(request.getName());
                comic.setDescription(request.getDescription());
                comic.setPublisher(request.getPublisher().isEmpty() ? "Đang cập nhật" : request.getPublisher());
                comic.setWriter(request.getWriter().isEmpty() ? "Đang cập nhật" : request.getWriter());
                comic.setArtist(request.getArtist().isEmpty() ? "Đang cập nhật" : request.getArtist());
                comic.setImage(null);
                comic.setImage(request.getName().toLowerCase());
                comic.setStatus(1);
                comic.setView(0);
                comic.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                comic.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                comic.setEnable(0);
                Set<String> strGenres = request.getGenre();
                Set<Genre> genres = new HashSet<>();
                if (strGenres == null){
                    genres.add(null);
                }else{
                    strGenres.forEach(genre ->{
                        Genre existGenre = genreRepository.findGenreByName(genre);
                        if(!(existGenre == null || existGenre.getName().isEmpty())){
                            genres.add(existGenre);
                        }
                    });
                }
                comic.setGenres(genres);
                comicRepository.save(comic);
                return comic;
            }catch(Exception e){
                throw new InternalServerException("Tạo mới thất bại");
            }
        }else{
            throw new BadRequestException("Bộ truyện này này đã tồn tại");
        }
    }

    @Override
    public Comic updateComic(long id,CreateComicRequest request){
        Comic comic = comicRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Comic With Id: " + id));;
        try{
            comic.setName(request.getName());
            comic.setDescription(request.getDescription());
            comic.setPublisher(request.getPublisher().isEmpty() ? "Đang cập nhật" : request.getPublisher());
            comic.setWriter(request.getWriter().isEmpty() ? "Đang cập nhật" : request.getWriter());
            comic.setArtist(request.getArtist().isEmpty() ? "Đang cập nhật" : request.getArtist());
            comic.setStatus(1);
            comic.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            Set<String> strGenres = request.getGenre();
            Set<Genre> genres = new HashSet<>();
            if (strGenres == null){
                genres.add(null);
            }else{
                strGenres.forEach(genre ->{
                    Genre existGenre = genreRepository.findGenreByName(genre);
                    if(!(existGenre == null || existGenre.getName().isEmpty())){
                        genres.add(existGenre);
                    }
                });
            }
            comic.setGenres(genres);
            comicRepository.save(comic);
            return comic;
        }catch(Exception e){
            throw new InternalServerException("Tạo mới thất bại");
        }
    }

    @Override
    public void enableComic(long id) {
        Comic comic = comicRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Comic With Id: " + id));
        if(comic.getEnable() == 1){
            comic.setEnable(0);
        } else{
            comic.setEnable(1);
        }
        comic.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        comicRepository.save(comic);
    }

    // @Override
    // public Comic updateImage(long id) {
    //     // TODO Auto-generated method stub
    //     Comic comic = comicRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Comic With Id: " + id));
    //     try{
    //         Map<String, Object> result = cloudinary.search()
    //             .expression("folder:" + comic.getName().toLowerCase())
    //             .execute();
    //         List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");
    //         // comic.setImageInfo(result == null || resources.isEmpty() ? null : resources.get(resources.size() - 1));
    //         String image = (String) resources.get(resources.size() - 1).get("folder");
    //         comic.setImage(image);
    //     }catch(Exception ex){
    //         throw new SomeRuntimeException("Set image is falling!", ex);
    //     }
    //     return comic;
    // }

}
