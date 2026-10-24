package org.yesidl12.centinelaseguraltda.security.jwt;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expiration;
    
    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }
    
    public String generateToken(UserDetails usuario) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("roles", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    public String extraerUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
