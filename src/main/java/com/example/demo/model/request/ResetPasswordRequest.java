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
public class ResetPasswordRequest {
    @NotNull(message = "Password rỗng")
    @NotEmpty(message = "Password rỗng")
    @Size(min = 5, max = 30, message = "Password từ 5 đén 30 kí tự")
    @Schema(description = "Password", example = "12345")
    private String newPassword;

    @NotNull(message = "Confirm Password rỗng")
    @NotEmpty(message = "Confirm Password rỗng")
    @Size(min = 5, max = 30, message = "Password từ 5 đén 30 kí tự")
    @Schema(description = "Confirm Password", example = "12345")
    private String confirmPassword;
}
