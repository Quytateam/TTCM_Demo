package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*",maxAge = 3600)
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private final ImageService imageService;

    @GetMapping("/file")
    public ResponseEntity<?> getFile(@RequestParam(value = "comicName") String comicName, @RequestParam(value ="chapName", required = false) String chapName){
        Map<String, Object> data = this.imageService.getFile(comicName,chapName);
        return new ResponseEntity<>(data, HttpStatus.OK);
        // Map<String, Object> data = this.imageService.getFiles(comicName,chapName);
        // return ResponseEntity.ok(data);
        // return ResponseEntity.ok("ok");
    }

    // @PostMapping("/upload-file")
    // public ResponseEntity<?> test(@RequestParam("file") MultipartFile file){
    //     Map data = this.imageService.upload(file);
    //     return new ResponseEntity<>(data, HttpStatus.OK);
    //     // return ResponseEntity.ok("ok");
    // }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
        @RequestParam(value = "comicName") String comicName, 
        @RequestParam(value ="chapName", required = false) String chapName) {
            Map<String, Object> data = this.imageService.upload(file, comicName, chapName);
            return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
