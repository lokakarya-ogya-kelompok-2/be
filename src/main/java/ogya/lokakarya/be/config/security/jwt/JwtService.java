package ogya.lokakarya.be.config.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import ogya.lokakarya.be.entity.User;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    private Key jwtKey;

    @PostConstruct
    public void init() {
        jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(User user) {
        final Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("full_name", user.getFullName());
        return Jwts.builder().claims(claims).subject(user.getId().toString()).issuedAt(now)
                .expiration(new Date(now.getTime() + jwtExpirationMs)).signWith(jwtKey).compact();
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) jwtKey).build().parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        return extractExpirationDate(token).after(new Date());
    }
}
