package com.example.demo.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Passwordresettoken;

public interface TokenRepository extends JpaRepository<Passwordresettoken, Integer>{
    Passwordresettoken findByToken(String token);

    @Query(value = "SELECT * FROM passwordresettoken WHERE user_id = :id", nativeQuery = true)
    Passwordresettoken findByUserId(Long id);

    @Query(value = "DELETE FROM passwordresettoken WHERE user_id = :id", nativeQuery = true)
    void deleteByUserId(Long id);
}
