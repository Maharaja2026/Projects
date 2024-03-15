package com.jdbctemplate.employee.util;

import com.jdbctemplate.employee.entity.Manager;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils
{
    private static String secretKey = "maharaja";
    private static long expiryDuration = 1 * 60;

    public String generateJwt(Manager manager)
    {
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;
        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);

        Claims claims = Jwts.claims()
                .setIssuer(manager.getManagerEmail().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);
        claims.put("name",manager.getManagerName());
        claims.put("email",manager.getManagerEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
//    public void verify(String authorization) throws Exception {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(authorization).getBody();
//        }catch (Exception e){
//            throw new Exception();
//        }
//
//    }

    public void verify(String authorization) throws SignatureException, ExpiredJwtException {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authorization).getBody();
        } catch (SignatureException e) {
            throw e;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            throw new SignatureException("Invalid token");
        }
    }


}
