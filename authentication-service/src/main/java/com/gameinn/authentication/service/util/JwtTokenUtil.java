package com.gameinn.authentication.service.util;

import com.gameinn.authentication.service.config.JwtConfig;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private final JwtConfig jwtConfig;

    @Autowired
    JwtTokenUtil(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(String email){
        Claims claims = Jwts.claims().setSubject(email);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + jwtConfig.getValidity();
        Date exp = new Date(expMillis);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256,jwtConfig.getSecret()).compact();
    }
}
