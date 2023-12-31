package org.bs.x2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bs.x2.dto.MemberDTO;
import org.bs.x2.service.MemberService;
import org.bs.x2.service.SocialService;
import org.bs.x2.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member/")
public class MemberController {

    private final MemberService memberService;

    private final SocialService socialService;

    private final JWTUtil jwtUtil;


    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO) {

        log.info("parameter: " + memberDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 로그인을 하려면 email , pw 가필요해서 memberDTO에서 끄집어내기
        MemberDTO result = memberService.login(
                memberDTO.getEmail(), memberDTO.getPw()
        );

        //AccessToken 만들기 - jwtUtil에 있는 generate 이용 - 만료 기간: 10분
        result.setAccessToken(jwtUtil.generate(Map.of("email", result.getEmail()), 1));

        //refreshToken 만들기 - 만료 기간 : 하루
        result.setRefreshToken(jwtUtil.generate(Map.of("email", result.getEmail()), 60 * 24));

        log.info("Return: " + result);

        return result;

    }

    // 인가 코드 가져오기
    @GetMapping("kakao")
    public MemberDTO getAuthCode(String code) {

        log.info("----------");
        log.info(code);

        String email = socialService.getKakaoEmail(code);

        MemberDTO memberDTO = memberService.getMemberWithEmail(email);

        return memberDTO;
    }

    // 인터셉터를 거치면 안됨 -> config에서 설정
    @RequestMapping("refresh")
    public Map<String, String> refresh(@RequestHeader("Authorization") String accessToken, String refreshToken) {

        log.info("refresh,  access: " + accessToken);

        log.info("refresh , refresh: " + refreshToken);

        // access 토큰 만료되었는지 확인

        // refresh 토큰 만료되지 않았는지 확인

        // 토큰 확인
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

//        // 10분 유지되는 accessToken 생성
//        Map.of("accessToken", jwtUtil.generate(claims , 10));
//
//        // 하루 유지되는 refreshToken 생성
//        Map.of("refreshToken" , jwtUtil.generate(claims,60*24));


        return Map.of("accessToken", jwtUtil.generate(claims, 1),
                "refreshToken", jwtUtil.generate(claims, 60 * 24));
    }

}