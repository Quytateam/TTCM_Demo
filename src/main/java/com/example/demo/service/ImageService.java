package com.example.demo.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    
    // String uploadFile(MultipartFile multipartFile) throws IOException;

    Map upload(MultipartFile file, String comicName, String chapName);

    Map<String, Object> getFile(String comicName, String chapName);

    // Map uploadPro(MultipartFile file, String comicName, String chapName);
}
