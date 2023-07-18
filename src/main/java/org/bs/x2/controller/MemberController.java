package org.bs.x2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bs.x2.dto.MemberDTO;
import org.bs.x2.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member/")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO){

        log.info("parameter: " + memberDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 로그인을 하려면 email , pw 가필요해서 memberDTO에서 끄집어내기
        MemberDTO result = memberService.login(
                memberDTO.getEmail() , memberDTO.getPw()
        );

        log.info("Return: " + result);

        return result;

    }
}