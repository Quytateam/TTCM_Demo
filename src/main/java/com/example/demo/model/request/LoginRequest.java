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
public class LoginRequest {
    @NotNull(message="Email rỗng")
    @NotEmpty(message="Email rỗng")
    @Size(min=3,max=30,message="Email có từ 3-30 ký tự")
    private String email;

    @NotNull(message = "Mật khẩu rỗng")
    @NotEmpty(message = "Mật khẩu rỗng")
    @Size(min=5,max=30,message="Mật khẩu có từ 6-30 ký tự")
    private String password;
}
