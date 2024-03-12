package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.ChangePasswordRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.security.service.UserDetailsImpl;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")    
    @Operation(summary="Lấy ra user bằng id")
    public ResponseEntity<Users> getUserById(@PathVariable long id){
        Users users = userService.getUserById(id);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            userService.changePassword(userDetails.getId(), request);
            return ResponseEntity.ok(new MessageResponse("Change Password Success!"));
        }
        throw new NotFoundException("Không tìm thấy tài khoản");
    }

    @GetMapping("/test")
    @Operation(summary = "Thử")
    public ResponseEntity<?> test(){
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailsImpl) {
        //     UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //     return ResponseEntity.ok(userDetails.getId());
        // }
        return ResponseEntity.ok("ok");
    }
}
