package com.example.demo.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
// import java.util.Random;
// import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
// import com.cloudinary.Transformation;
// import com.cloudinary.Transformation;
// import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.response.SomeRuntimeException;
import com.example.demo.service.ImageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final Cloudinary cloudinary;
    
    // @Override
    // public String uploadFile(MultipartFile multipartFile){
    //     try{
    //         String data = cloudinary.uploader()
    //         .upload(multipartFile.getBytes(), 
    //             Map.of("public_id", UUID.randomUUID().toString()))
    //         .get("url").toString();
    //         return  data;
    //     }catch(IOException io){
    //         throw new RuntimeException("Image upload fail");
    //     }
    // }

    // @Override
    // public Map upload(MultipartFile file)  {
    //     try{
    //         ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    //         HttpSession session = attr.getRequest().getSession(true);
    //         Integer counter = Optional.ofNullable((Integer) session.getAttribute("counter")).orElse(1);
    //         String comicName = "Spiderman";
    //         String chapName = "1";
    //         String fileName = comicName + "-" + chapName + "-" + counter;
    //         String publicId =  comicName+"/" + chapName + "/" + fileName;
    //         Map params = ObjectUtils.asMap(
    //             "public_id", publicId
    //         );
    //         // Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
    //         Map data = this.cloudinary.uploader().upload(file.getBytes(), params);
    //         session.setAttribute("counter", counter + 1);
    //         return data;
    //     }catch (IOException io){
    //         throw new RuntimeException("Image upload fail");
    //     }
    // }

    @Override
    public Map<String, Object> upload(MultipartFile file, String comicName, String chapName)  {
        try{
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            Integer counter = Optional.ofNullable((Integer) session.getAttribute("counter")).orElse(1);

            String fileName = chapName.isEmpty() ? comicName.toLowerCase() + "-avata" : comicName.toLowerCase() + "-" + chapName.toLowerCase() + "-" + counter;
            String publicId = chapName.isEmpty() ? comicName.toLowerCase() +"/" + fileName: comicName.toLowerCase() + "/" + chapName.toLowerCase() + "/" + fileName;
            Map<String, Object> params = ObjectUtils.asMap(
                "public_id", publicId
            );
            // Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            Map<String, Object> data = this.cloudinary.uploader().upload(file.getBytes(), params);
            session.setAttribute("counter", counter + 1);
            return data;
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }

    public ApiResponse getFile(String comicName, String chapName) {
        try{
            // String publicId1 =  comicName+"/" + chapName;
            String publicId = chapName.isEmpty() ? comicName.toLowerCase() : comicName.toLowerCase() + "/" + chapName.toLowerCase();
            // Retrieve all files in the specified folder
            ApiResponse result = cloudinary.search()
            .expression("folder:" + publicId).sortBy("filename", "asc")
            .execute();
            return result;
        }catch(Exception ex){
            throw new SomeRuntimeException("the sky is falling!", ex);
        }
    }

    public ApiResponse getFiles(String comicName, String chapName) {
        try{
            // String publicId1 =  comicName+"/" + chapName;
            String publicId = chapName.isEmpty() ? comicName.toLowerCase() : comicName.toLowerCase() + "/" + chapName.toLowerCase();
            // Retrieve all files in the specified folder
            ApiResponse result = cloudinary.search()
            .expression("folder:" + publicId).sortBy("filename", "asc")
            .execute();
            return result;
        }catch(Exception ex){
            throw new SomeRuntimeException("the sky is falling!", ex);
        }
    }

    // public String getFiles(String comicName, String chapName) {
    //     try{
    //         String publicId = chapName.isEmpty() ? comicName.toLowerCase() : comicName.toLowerCase() + "/" + chapName.toLowerCase() + "/spiderman-1-1";
    //         Map<String, String> options = ObjectUtils.asMap("alt", "Beautiful landscape with mountains and lake");
    //         String imageTag = cloudinary.url().imageTag(publicId + ".jpg", options);
    //         return imageTag;
    //     }catch(Exception ex){
    //         throw new SomeRuntimeException("the sky is falling!", ex);
    //     }
    // }

    // public String getFile() {
    //     String comicName = "Spiderman";
    //     String chapName = "1";
    //     // Xây dựng đường dẫn của thư mục con
    //     String folderPath = comicName + "/" + chapName;
    //     // Map<String, Object> result = cloudinary.search()
    //     //     .expression("folder:" + folderPath)
    //     //     .execute();
    //     String result = cloudinary.url().generate(folderPath);
    //     // String result = cloudinary.url().format("json").type("list").transformation(new Transformation().fetchFormat("json")).generate(folderPath);
    //     return result;
    //     // Lấy thông tin về file từ Cloudinary
    //         // Map result = cloudinary.uploader().explicit(publicId, ObjectUtils.emptyMap());
    //         // return result;    
    // }

    // public String getFile(){
    //     try {
    //         ApiResponse apiResponse = cloudinary.api().resourceByAssetID("ddba90f8b93ce2c7ab8e3ecba1eb5941", ObjectUtils.emptyMap());
    //     }catch (Exception ex){
    //         throw new SomeRuntimeException("the sky is falling!", ex);
    //     }
    // }
}
