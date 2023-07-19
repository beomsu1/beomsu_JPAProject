package org.bs.x2.controller.interceptor;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bs.x2.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class JWTInterceptor implements HandlerInterceptor {

    private final JWTUtil jwtUtil;

    // pre
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        // preflight는 그냥 지나가게 설정
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }

        // oauth 방식이라면 - "Authorization" 이 날라옴 Bearer 엑세스토큰
        // accessToken 확인
        // 예외처리 -> case로 메세지 보내기 , refresh 가져와 ! -> refresh 만료확인
        // JSON 데이터를 만들어서 보내줘야함

        try {
            String headerStr = request.getHeader("Authorization");

            if(headerStr == null || headerStr.length()<7){
                throw new JWTUtil.CustomJWTException("NullToken");
            }

            String accessToken = headerStr.substring(7);

            // 내용물 확인
            Map<String , Object> claims = jwtUtil.validateToken(accessToken);

            log.info("result: " + claims);

            // 하나씩 에외처리를 잡아줘야함
        }catch (Exception e){

            // 내가 보내는 타입은 json이야! 라는 뜻
            response.setContentType("application/json");

            Gson gson = new Gson();

            String str = gson.toJson(Map.of("error" , e.getMessage()));

            response.getOutputStream().write(str.getBytes());

            return false;
        }



        log.info("--------------");
        log.info(handler);
        log.info("--------------");
        log.info(jwtUtil);
        log.info("--------------");


        return true;
    }
}
