package com.example.demo.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChapterRequest {

    private long chapterId;

    @NotNull(message = "Tên chapter rỗng")
    @NotEmpty(message = "Tên chapter rỗng")
    @Size(min=1,max=50,message="Độ dài chapter từ 1-50 ký tự")
    private String name;

    private String content;
}
