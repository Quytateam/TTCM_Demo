package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Chapter;
import com.example.demo.entity.Comic;
import com.example.demo.entity.Comment;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateComicRequest;
import com.example.demo.model.request.CreateCommentRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.security.service.UserDetailsImpl;
import com.example.demo.service.ChapterService;
import com.example.demo.service.ComicService;
import com.example.demo.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comic")
@CrossOrigin(origins = "*",maxAge = 36000)
public class ComicController {
    
    @Autowired
    private ComicService comicService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ChapterService chapterService;

    @GetMapping("")
    @Operation(summary="Lấy ra danh sách truyện")
    public ResponseEntity<List<Comic>> getList(){
        List<Comic> list = comicService.getList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{namevsid}")
    public ResponseEntity<?> getComic(@PathVariable String namevsid){
        Comic comic = comicService.getComic(namevsid);

        return ResponseEntity.ok(comic);
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<Comic>> getListEnable(){
        List<Comic> list = comicService.getListEnabled();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComic(@Valid @RequestBody CreateComicRequest request){
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
        //     UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
        //     String authorities = userDetails.getAuthorities().stream()
        //             .map(GrantedAuthority::getAuthority)
        //             .collect(Collectors.joining(", "));
        //     if(authorities.equals("ROLE_ADMIN")){
        //         Genre genre = genreService.createGenre(request);
        //         return ResponseEntity.ok(genre);  
        //     }
        //     throw new BadRequestException("Không thuộc thẩm quyền");
        // }
        // throw new BadRequestException("Không tìm thấy tài khoản");

        Comic comic = comicService.createComic(request);
        return ResponseEntity.ok(comic);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Comic> updateComic(@PathVariable long id,@RequestBody CreateComicRequest request){
        Comic comic = comicService.updateComic(id, request);

        return ResponseEntity.ok(comic);
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<?> enabled(@PathVariable long id){
        comicService.enableComic(id);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }

    @GetMapping("/{comicName}/{chapName}")
    public ResponseEntity<?> getChapter(@PathVariable String comicName, @PathVariable String chapName){
        Chapter chapter = chapterService.getChapter(comicName, chapName);
        // return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
        return ResponseEntity.ok(chapter);
    }

    @PostMapping("/{namevsid}/comment")
    public ResponseEntity<?> createComment(@PathVariable String namevsid, @RequestBody CreateCommentRequest request){
        // Comic comic = comicService.getComic(namevsid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // userService.changePassword(userDetails.getId(), request);
            Comment comment = commentService.createComment(namevsid, userDetails.getId(), request);
            return ResponseEntity.ok(comment);
        }
        throw new NotFoundException("Không tìm thấy tài khoản");
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable long id){
        // Comic comic = comicService.getComic(namevsid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // userService.changePassword(userDetails.getId(), request);
            commentService.deleteComment(userDetails.getId(), id);
            return ResponseEntity.ok("Đã xóa thành công");
        }
        throw new NotFoundException("Không tìm thấy tài khoản");
    }

    // @PatchMapping("/img/{id}")
    // public ResponseEntity<?> getComic(@PathVariable long id){
    //     Comic comic = comicService.updateImage(id);
    //     return ResponseEntity.ok(comic);
    // }
}
