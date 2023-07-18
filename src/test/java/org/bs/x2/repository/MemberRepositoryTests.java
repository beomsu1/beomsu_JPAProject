package org.bs.x2.repository;

import lombok.extern.log4j.Log4j2;
import org.bs.x2.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 추가
    @Test
    public void testInsert(){

        Member member = Member.builder()
                .email("beomsu1@aaa.com")
                .pw("1111")
                .nickname("beomsu")
                .build();

        memberRepository.save(member);
    }

    //회원 조회
    @Test
    public void testRead(){

        String email = "beomsu1@aaa.com";

        Optional<Member> result = memberRepository.findById(email);

        Member member = result.orElseThrow();

        log.info(member);
    }
}
