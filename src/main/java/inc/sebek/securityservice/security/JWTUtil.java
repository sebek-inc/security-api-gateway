package inc.sebek.securityservice.security;

import inc.sebek.securityservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String ROLE = "role";

    @Value("${jwt.jjwt.secret}")
    private String secret;

    @Value("${jwt.jjwt.expiration}")
    private String expirationTime;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                   .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                   .parseClaimsJws(token)
                   .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        final var expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        var claims = new HashMap<String,Object>();
        claims.put(ROLE, user.getRoles());
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String,Object> claims, String username) {
        var expirationTimeLong = Long.parseLong(expirationTime); //in second
        final var createdDate = new Date();
        final var expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(username)
                   .setIssuedAt(createdDate)
                   .setExpiration(expirationDate)
                   .signWith(SignatureAlgorithm.HS512,
                             Base64.getEncoder().encodeToString(secret.getBytes()))// TODO REMOVE DEPRECATED CALL
                   .compact();
    }
}