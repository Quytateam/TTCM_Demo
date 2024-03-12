package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.model.request.ChangePasswordRequest;
import com.example.demo.model.request.ForgetPasswordRequest;
import com.example.demo.model.request.Register;
import com.example.demo.model.request.ResetPasswordRequest;

public interface UserService {
    Users register(Register register);

    Users getUserById(long id);

    void changePassword(long id, ChangePasswordRequest request);

    // Users getUserByEmail(String email);
    String sendEmail(ForgetPasswordRequest request);

    String resetPassword(long id, ResetPasswordRequest request);
}
