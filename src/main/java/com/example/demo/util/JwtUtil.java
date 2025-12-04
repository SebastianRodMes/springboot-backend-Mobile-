package com.example.demo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

     // Clave secreta (en producción esto va en variables de entorno)
     private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

     //duracion del token 
     private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

     //generar token
     public String generateToken(String email, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    //validanis token

    public boolean valdiateToken(String token){
         try{
            Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token);
            return true;
            
         }catch(Exception e){
            return false;
         }
    }

    //extraer email del token
    public String extractEmail(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
