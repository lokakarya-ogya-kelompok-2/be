package ogya.lokakarya.be.config.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    private Key jwtKey;

    @PostConstruct
    public void init() {
        jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date now = new Date();
        String authoritiesStr = userDetails.getAuthorities().stream()
                .map(authority -> "ROLE_" + authority.getAuthority())
                .collect(Collectors.joining(","));
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername()).issuedAt(now)
                .claim("roles", authoritiesStr)
                .expiration(new Date(now.getTime() + jwtExpirationMs)).signWith(jwtKey).compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
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

    public Collection<GrantedAuthority> extractRoles(String token) {
        return extractClaim(token, claims -> {
            String authoritiesString = claims.get("roles", String.class);
            return Arrays.stream(authoritiesString.split(",")).map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });
    }

    public boolean isTokenValid(String token) {
        return extractExpirationDate(token).after(new Date());
    }
}
