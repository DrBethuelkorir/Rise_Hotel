package com.hotel.Rise.Security.Utils;

import com.hotel.Rise.Security.User.roomUserDetails;
import io.jsonwebtoken.*;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.security.Key;
import io.jsonwebtoken.SignatureException;
import java.util.Date;

public class JWTUtils {

    @Value("${auth.token.jwtsecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationinmils}")
    private int expirationTime;

    public String generateToken(Authentication authentication) {
        roomUserDetails userPrinciple = (roomUserDetails) authentication.getPrincipal();

        return  Jwts.builder().setSubject(userPrinciple.getEmail())
                .claim("id",userPrinciple.getId())
                .claim("roles",userPrinciple.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expirationTime) )
                .signWith(key(),SignatureAlgorithm.HS512).compact();
    }
    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public  boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |SignatureException |
                 IllegalArgumentException e) {
            throw new JwtException(e.getMessage());

        }
    }
}
