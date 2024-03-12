package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.reponsitory.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UsersRepository usersRepository;

    // @Override
    // @Transactional
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     throw new UsernameNotFoundException("This method is not supported. Use loadUserById instead.");
    // }

    // @Transactional
    // public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
    //     // TODO Auto-generated method stub
    //     Users users = usersRepository.findById(id)
    //     .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
    //     // if(user != null && user.isEnabled()){    
    //     //     return UserDetailsImpl.build(user);
    //     // }else{
    //     //     throw new UsernameNotFoundException("User Not Found with username: " + username);
    //     // }
    //     return UserDetailsImpl.build(users); 
    // }
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        Users user = usersRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return UserDetailsImpl.build(user);
    }
}
