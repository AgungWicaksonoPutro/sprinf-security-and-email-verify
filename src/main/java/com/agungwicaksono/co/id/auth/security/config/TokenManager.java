package com.agungwicaksono.co.id.auth.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
public class TokenManager implements Serializable {
    private static final long TOKEN_VALIDITY = 10 * 60 * 60;
    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;

    public String generateJwtToken(UserDetails userDetails){
        log.info("data masuk di config token manager function generate jwt token {}", userDetails);
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }
    public boolean validateJwtToken(String token, UserDetails userDetails){
        log.info("data masuk di config token manager function validate token {} & membawa token {}", userDetails, token);
        String userName = getUserNameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        log.info("di validate jwt token isi dari claims {}", claims);
        Boolean isTokenExpired = claims.getExpiration().before(new Date(System.currentTimeMillis()));
        return (userName.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUserNameFromToken(String token){
        log.info("membawa token di proses di token manager function get user name from token {}", token);
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        log.info("Membuat class claims {}", claims);
        return claims.getSubject();
    }
}
