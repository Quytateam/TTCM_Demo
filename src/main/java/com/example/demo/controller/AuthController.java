package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.model.request.ForgetPasswordRequest;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.Register;
import com.example.demo.model.request.ResetPasswordRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.model.response.UserInfoResponse;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.service.UserDetailsImpl;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",maxAge = 36000)
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/register")
    @Operation(summary = "Đăng kí")
    public ResponseEntity<?> register(@Valid @RequestBody Register registers){
      
        Users user = userService.register(registers);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary="Đăng nhập")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail().contains("@")? loginRequest.getEmail().split("@")[0] : loginRequest.getEmail();
         Authentication authentication = authenticationManager
                 .authenticate(new UsernamePasswordAuthenticationToken(email,
                         loginRequest.getPassword()));

         SecurityContextHolder.getContext().setAuthentication(authentication);

         UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

         if (userDetails.getEnable() == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tài khoản chưa được kích hoạt.");
        }

         ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

         List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String role = String.join(",", roles);

         return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
               .body(new UserInfoResponse(userDetails.getId(),
                       userDetails.getUsername(),
                       userDetails.getEmail(),
                       role,
                       jwtCookie.getValue()));
        //   return ResponseEntity.ok(role);
    }

    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất")
    public ResponseEntity<?> logout(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("Bạn đã đăng xuất"));
    }

    @PostMapping("/forgot-password")
    public String forgotPassordProcess(@Valid @RequestBody ForgetPasswordRequest request){
        return userService.sendEmail(request);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request, @RequestParam("id") long id){
        String result = userService.resetPassword(id, request);
        return ResponseEntity.ok(result);
    }
}
