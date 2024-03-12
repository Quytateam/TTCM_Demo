package com.example.demo.reponsitory;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{

    Optional<Users> findByUsername(String username);
    
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Users findByEmail(String email);

    // Boolean existsByUsername(String username);

    // Boolean existsByEmail(String email);
}
