package com.gameinn.api.gateway.security;

import com.gameinn.api.gateway.config.JwtConfig;
import com.gameinn.api.gateway.exception.JwtTokenIncorrectStructureException;
import com.gameinn.api.gateway.exception.JwtTokenMalformedException;
import com.gameinn.api.gateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    private final JwtConfig jwtConfig;

    @Autowired
    JwtTokenUtil(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public void validateToken(final String header) throws JwtTokenMalformedException, JwtTokenMissingException{
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
