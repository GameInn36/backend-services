package com.gameinn.authentication.service.util;

import com.gameinn.authentication.service.config.JwtConfig;
import com.gameinn.authentication.service.exception.JwtTokenIncorrectStructureException;
import com.gameinn.authentication.service.exception.JwtTokenMalformedException;
import com.gameinn.authentication.service.exception.JwtTokenMissingException;
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

    public String generateToken(String id){
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + jwtConfig.getValidity() * 1000 * 60;
        Date exp = new Date(expMillis);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256,jwtConfig.getSecret()).compact();
    }

    public void validateToken(final String header) throws JwtTokenMalformedException, JwtTokenMissingException {
        try{
            String[] parts = header.split(" ");
            if(parts.length != 2 || !"Bearer".equals(parts[0])){
                throw new JwtTokenIncorrectStructureException("Incorrect Authentication Structure");
            }

            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(parts[1]);
        } catch(SignatureException e) {
            throw new JwtTokenMalformedException("Invalid JWT Signature");
        } catch (MalformedJwtException e){
            throw new JwtTokenMalformedException("Invalid JWT Token");
        } catch (ExpiredJwtException e){
            throw new JwtTokenMalformedException("Expired JWT Token");
        } catch (UnsupportedJwtException e){
            throw new JwtTokenMalformedException("Unsupported JWT Token");
        } catch (IllegalArgumentException e){
            throw new JwtTokenMissingException("JWT claims string is empty");
        }
    }
}
