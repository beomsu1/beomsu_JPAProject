package org.bs.x2.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Log4j2
public class JWTTests {

    @Autowired
    private JWTUtil jwtUtil;

    // jwt 문자열 생성
    @Test
    public void testCreate(){

        Map<String,Object> claim = Map.of("email","beomsu1@aaa.com");

        String jwtStr = jwtUtil.generate(claim,10);

        log.info("------------");
        log.info(jwtStr);
    }

    // 토큰 확인
    @Test
    public void testToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImJlb21zdTFAYWFhLmNvbSIsImlhdCI6MTY4OTc0NDM3NywiZXhwIjoxNjg5NzQ0OTc3fQ.4JKb7oHT2lUVgVIEb-NUcg3Pj3MwhbOzSiO7ABjURVM";

    try{

        jwtUtil.validateToken(token);

    }catch (Exception e){
        log.info(e);
    }

    }
}
