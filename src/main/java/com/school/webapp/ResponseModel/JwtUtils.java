package com.school.webapp.ResponseModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final String SECRET_KEY="salamayotunde";


    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public String extractSchoolEmail(String token){
        return extractClaim(token,Claims::getAudience);
    }
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaim(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaim(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails,String schoolid){
        Map<String,Object> claims=new HashMap<>();
        return CreateToken(claims,userDetails,schoolid);
    }
    private String CreateToken( Map<String,Object> claims,UserDetails userDetails,String schoolid){
        return Jwts.builder().addClaims(claims).setSubject(userDetails.getUsername()).setAudience(schoolid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }
    public Boolean validateToken(String token,UserDetails userDetails){

          final String username=extractUsername(token);
          return (username.equals(userDetails.getUsername())&&! isTokenExpired(token));
    }
    public String extractEmailForVerification(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public String generateTokenForEmailVerification(String schoolid){
        Map<String,Object> claims=new HashMap<>();
        return CreateTokenForEmailVerification(claims,schoolid);
    }
    private String CreateTokenForEmailVerification( Map<String,Object> claims,String schoolid){
        return Jwts.builder().addClaims(claims).setSubject(schoolid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }
    public Boolean validateTokenForVerification(String token){

        final String schoolid=extractEmailForVerification(token);
        return (! isTokenExpired(token));
    }

}
