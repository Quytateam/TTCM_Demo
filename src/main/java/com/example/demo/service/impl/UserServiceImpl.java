package com.example.demo.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ERole;
import com.example.demo.entity.Passwordresettoken;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.ChangePasswordRequest;
import com.example.demo.model.request.ForgetPasswordRequest;
import com.example.demo.model.request.Register;
import com.example.demo.model.request.ResetPasswordRequest;
import com.example.demo.reponsitory.RoleRepository;
import com.example.demo.reponsitory.TokenRepository;
import com.example.demo.reponsitory.UsersRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public Users register(Register request){
      Users existingUser = usersRepository.findByEmail(request.getEmail());
      if(existingUser != null){
        throw new BadRequestException("Tài khoản đã tồn tại");
      }
      Users user = new Users();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername() != "" ? request.getUsername() : request.getEmail().split("@")[0]);
        user.setPassword(encoder.encode(request.getPassword()));
        String role = request.getRole();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole((role == null || role == "")?  userRole: adminRole );
        user.setEnable(1);
        usersRepository.save(user);
        return user;
    }

    @Override
    public Users getUserById(long id){
      Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found User"));
      return user;
    }

    @Override
    public void changePassword(long id, ChangePasswordRequest request) {
      // TODO Auto-generated method stub
      Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found User"));
      if(!encoder.matches(request.getOldPassword(), user.getPassword())){
        throw new BadRequestException("Old Passrword Not Same");
      }
      user.setPassword(encoder.encode(request.getNewPassword()));

      usersRepository.save(user);  
    }

    @Override
    public String sendEmail(ForgetPasswordRequest request){
      if(request.getEmail() == null){throw new BadRequestException("Vui lòng nhập email");}
      Users user = usersRepository.findByEmail(request.getEmail());
      if(user == null){throw new BadRequestException("Không tìm thấy tài khoản");}
      Passwordresettoken passwordresettoken = tokenRepository.findByUserId(user.getId());
      if(passwordresettoken != null){throw new BadRequestException("After one hour you can request for another token!");}
      String token = jwtUtils.generateRandomToken();
      Passwordresettoken resetToken = new Passwordresettoken();
      resetToken.setToken(token);
      resetToken.setUser(user);
      tokenRepository.save(resetToken);
      String resetPasswordUrl = "http://localhost:8080/api/auth/reset-password?token=" + token + "&id=" + user.getId();
      try{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mailtrap@demomailtrap.com");
        message.setTo("6151071090@st.utc2.edu.vn");
        message.setSubject("Reset Password Link");
        message.setText("Click here to reset your password: " + resetPasswordUrl);
        emailSender.send(message);
        return "Link sent to your email!";
      }catch(Exception e){
        // tokenRepository.deleteByUserId(user.getId());
        throw new BadRequestException("Không thể gửi email đặt lại mật khẩu. Vui lòng thử lại sau.");
      }
    }

    @Override
    public String resetPassword(long id, ResetPasswordRequest request){
      // if(request.getNewPassword() == null){throw new BadRequestException("Vui lòng nhập mật khẩu mới");}
      // if(request.getConfirmPassword() == null){throw new BadRequestException("Vui lòng xác thực lại nhập mật khẩu mới");}
      if(!request.getNewPassword().equals(request.getConfirmPassword())){throw new BadRequestException("Mật khẩu xác thực không đúng");}
      Users user = usersRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
      if(encoder.matches(request.getNewPassword(), user.getPassword())){
        throw new BadRequestException("The new password must be different from the old one!");
      }
      user.setPassword(encoder.encode(request.getNewPassword()));
      usersRepository.save(user); 
      tokenRepository.deleteByUserId(id);
      return "Your password reser succesfully";
    }
}
