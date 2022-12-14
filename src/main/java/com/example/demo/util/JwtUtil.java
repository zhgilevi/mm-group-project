package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());

  @Value("${messanger.app.jwtSecret}")
  private String jwtSecret;

  @Value("${messanger.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(String username){

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date((new Date(System.currentTimeMillis()).getTime()+jwtExpirationMs)))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String authToken) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken){

    try{
    Claims claims = Jwts.parser().setSigningKey(jwtSecret)
        .parseClaimsJws(authToken).getBody();


    if (claims.getExpiration().before(new java.util.Date(System.currentTimeMillis()))){
      return false;
    }
    return true;
    }
    catch (SignatureException e) {

      logger.log(Level.SEVERE, "Invalid JWT signature: {}", e.getMessage());
      return false;
    }
    catch (MalformedJwtException e) {

      logger.log(Level.SEVERE, "Invalid JWT token: {}", e.getMessage());
      return true;
    }
    catch (ExpiredJwtException e) {

      logger.log(Level.SEVERE, "JWT token is expired: {}", e.getMessage());
      return false;
    } catch (UnsupportedJwtException e) {

      logger.log(Level.SEVERE, "JWT token is unsupported: {}", e.getMessage());
      return false;
    } catch (IllegalArgumentException e) {

      logger.log(Level.SEVERE, "JWT claims string is empty: {}}", e.getMessage());
      return false;
    }

  }

}
