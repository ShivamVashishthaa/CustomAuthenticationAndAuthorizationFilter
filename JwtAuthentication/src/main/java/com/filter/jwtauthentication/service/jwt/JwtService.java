package com.filter.jwtauthentication.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${my.secret.key}")
    private String secretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getTokenFromHeader(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }
        return "Not a token";
    }

    // Generate JWT by username
    public String generateJwtTokenByUsername(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(getSecretKey())
                .compact();
    }

    //extract Claims From Token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // extract claim generic method
    private <R> R extractClaim(String token, Function<Claims, R> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    // extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isExpired(String token){
       return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isExpired(token));
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported token");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid token");
        } catch (SignatureException e) {
            System.out.println("Signature mismatch");
        } catch (IllegalArgumentException e) {
            System.out.println("Token empty");
        }
        return false;
    }

}
