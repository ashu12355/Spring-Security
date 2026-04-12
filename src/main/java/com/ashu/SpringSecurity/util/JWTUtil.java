package com.ashu.SpringSecurity.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
//    @Value("${auth.jwt-secret}")
    private String SECRET = "my-super-secret-local-keyashu12355@gmailcom-tobecarefullwhileusing";

    @Value("${auth.expiration}")
    private long Expiration;

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());


    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+Expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
