package org.bs.x2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bs.x2.dto.MemberCartDTO;
import org.bs.x2.service.MemberCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/cart/")
public class MemberCartController {

    private final MemberCartService cartService;

    // 카트 추가
    @PostMapping("add")
    // 배열로 목록 데이터를 받아야함
    public List<MemberCartDTO> addCart (@RequestBody  MemberCartDTO memberCartDTO){

        log.info(memberCartDTO);

        return cartService.addCart(memberCartDTO);

    }

    // 카트 가져오기
    @GetMapping("{email}")
    public List<MemberCartDTO> getCart (@PathVariable("email") String email){

        log.info(email);

       return cartService.getCart(email);
    }

}
