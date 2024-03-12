package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Comic;
import com.example.demo.entity.Genre;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateGenreRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.security.service.UserDetailsImpl;
import com.example.demo.service.GenreService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genre")
@CrossOrigin(origins = "*",maxAge = 3600)
public class GenreController {
    
    @Autowired
    private GenreService genreService;

    @GetMapping("/")
    public ResponseEntity<?> getListGenre(){
        List<Genre> genres = genreService.findAll();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<Genre>> getListEnabled(){
        List<Genre> genres = genreService.getListEnabled();
        return ResponseEntity.ok(genres);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGnere(@Valid @RequestBody CreateGenreRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
            if(authorities.equals("ROLE_ADMIN")){
                Genre genre = genreService.createGenre(request);
                return ResponseEntity.ok(genre);
                
            }
            throw new BadRequestException("Không thuộc thẩm quyền");
        }
        throw new NotFoundException("Không tìm thấy tài khoản");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable long id, @Valid @RequestBody CreateGenreRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
            if(authorities.equals("ROLE_ADMIN")){
                Genre genre = genreService.updateGenre(id, request);
                return ResponseEntity.ok(genre);
                
            }
            throw new BadRequestException("Không thuộc thẩm quyền");
        }
        throw new NotFoundException("Không tìm thấy tài khoản");
    }

    @PatchMapping("/enable/{id}")
    @Operation(summary="Kích hoạt danh mục bằng id")
    public ResponseEntity<?> enabled(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
            if(authorities.equals("ROLE_ADMIN")){
                genreService.enableGenre(id);
                return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
                
            }
            throw new BadRequestException("Không thuộc thẩm quyền");
        }
        throw new NotFoundException("Không tìm thấy tài khoản");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa danh mục bằng id")
    public ResponseEntity<?> delete(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
            if(authorities.equals("ROLE_ADMIN")){
                genreService.deleteGenre(id);
                return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
                
            }
            throw new BadRequestException("Không thuộc thẩm quyền");
        }
        throw new NotFoundException("Không tìm thấy tài khoản");
    }

    @GetMapping("/{genrename}")
    public ResponseEntity<List<Comic>> getListComicByGenre(@PathVariable String genrename){
        List<Comic> list =  genreService.getListComicByGenre(genrename);
        return ResponseEntity.ok(list);
    }

    @GetMapping("")
    @Operation(summary="Tìm kiếm sản phẩm bằng keyword")
    public ResponseEntity<List<Comic>> searchComic(@RequestParam("keyword") String keyword){
        List<Comic> list = genreService.searchComic(keyword.toLowerCase());
        return ResponseEntity.ok(list);
    }
}
