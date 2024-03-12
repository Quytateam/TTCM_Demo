package com.example.demo.model.request;

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
public class CreateCommentRequest {
    
    @NotNull(message = "Nội dung bình luận rỗng")
    @NotEmpty(message="Nội dung bình luận rỗng")
    @Schema(description = "Bình luận", example="Cũng khá đc")
    @Size(min=1,max=1000,message="Mô tả truyện từ 5-1000 ký tự")
    private String comment;
}
