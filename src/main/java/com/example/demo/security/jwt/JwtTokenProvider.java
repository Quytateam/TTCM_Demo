package com.example.demo.security.jwt;

import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenProvider {
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
