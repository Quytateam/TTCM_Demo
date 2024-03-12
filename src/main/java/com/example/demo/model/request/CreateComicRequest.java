package com.example.demo.model.request;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateComicRequest {
    @NotNull(message = "Tên truyện rỗng")
    @NotEmpty(message = "Tên truyện rỗng")
    @Size(min=3,max=50,message="Độ dài truyện từ 5-50 ký tự")
    private String name;

    @NotNull(message = "Mô tả rỗng")
    @NotEmpty(message="Mô tả rỗng")
    @Schema(description = "Mô tả truyện",example="Đây là truyện thứ 1")
    @Size(min=1,max=1000,message="Mô tả truyện từ 5-1000 ký tự")
    private String description;

    private String publisher;

    private String writer;

    private String artist;

    // private String image;

    // private Integer status;

    // private long view;

    private Set<String> genre;
}
