package com.example.demo.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {

    private String username;

    @NotNull(message = "Email rỗng")
    @NotEmpty(message = "Email rỗng")
    @Size(min = 6, max = 30, message = "Email từ 6 đén 30 kí tự")
    @Email(message = "Email không hợp lệ")
    @Schema(description = "Email", example = "ABC@gmail.com", required = true)
    private String email;

    @NotNull(message = "Password rỗng")
    @NotEmpty(message = "Password rỗng")
    @Size(min = 5, max = 30, message = "Password từ 5 đén 30 kí tự")
    @Schema(description = "Password", example = "12345")
    private String password;

    private String role;
}
