package com.example.myapp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.example.myapp.dto.UserDTO;
import com.example.myapp.utils.DateUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private int expire = 3600;
    private String secret = "+J4SgUkGRJ1HN4DxY+/fFqxTcWDGYDuytAdFxJGzoneCXgQyX9gu6ufHD/FEesCn";

    public String generateToken(UserDTO user) {

        Long expire = DateUtil.addCurrentDateSeconds(this.expire);
        Map<String, String> claims = new HashMap<>();
        
        Integer userId = user.getId();

        claims.put("id",  userId.toString());
        claims.put("name", user.getName());
        claims.put("lastname", user.getLastname());
        claims.put("email", user.getEmail());

        JwtBuilder jwt = Jwts.
        builder()
        .claims(claims)
        .issuer(user.getEmail())
        .subject(user.getEmail())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(expire))
        .signWith(getTokenDecode());

        return jwt.compact();
    }

    public Claims getAllClaims(String token) throws ExpiredJwtException {
        Claims claims = Jwts.parser().verifyWith(getTokenDecode()).build().parseSignedClaims(token).getPayload();
        return claims;
    }

    public Object getClaim(String token, String claim) throws ExpiredJwtException {
        Claims claims = getAllClaims(token);
        Object value = claims.get(claim);
        return value;
    }

    public SecretKey getTokenDecode() {
        byte[] decodeByte = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodeByte);
    }
}
