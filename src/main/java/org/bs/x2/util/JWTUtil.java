package org.bs.x2.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    // 예외 생성
    public static class CustomJWTException extends RuntimeException{

        public CustomJWTException(String msg){
            super(msg);
        }
    }

    @Value("${org.bs.jwt.secret}")
    private String key;

    // 토큰 생성
    public String generate(Map<String, Object> claiMap , int min){

        // 헤더 생성
        // 모든 데이터는 Map
        Map<String,Object> headers = new HashMap<>();
        headers.put("typ" , "JWT");

        //claims
        Map<String,Object> claims = new HashMap<>();
        claims.putAll(claiMap);

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){

        }
        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                // zondeDateTime = 우리가 사용하는 시간 (Asia/Seoul)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                // 만료시간
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                // compact 문자열로 만들어줌
                .compact();

        return jwtStr;

    }

    // 내용물 가져오는 메소드 ( 토큰 확인 )
    public Map<String ,Object> validateToken(String token){

        Map<String , Object> claims = null;
        
        // 토큰이 NULL일 때
        if(token == null){
            throw new CustomJWTException("NullToken");
        }

        try{
            SecretKey key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));

            claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();

            // JWT가 올바르게 구성되지 않았을 때
        }catch (MalformedJwtException e){
            throw new CustomJWTException(("MalFormed"));
            // 만료됐을 때
        }catch (ExpiredJwtException e){
            throw new CustomJWTException(("Expired"));
            // 잘못 구성되어 있을 때
        }catch (InvalidClaimException e){
            throw new CustomJWTException(("Invalid"));
            // 나머지 JWTException
        }catch (JwtException e){
            throw new CustomJWTException(e.getMessage());
        }catch (Exception e){
            throw new CustomJWTException(("Error"));
        }

        return claims;
    }
}
