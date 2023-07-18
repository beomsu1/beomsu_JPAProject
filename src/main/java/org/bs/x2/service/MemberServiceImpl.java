package org.bs.x2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bs.x2.dto.MemberDTO;
import org.bs.x2.entity.Member;
import org.bs.x2.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    // 예외 처리
    // RuntimeException을 사용한 이유는 uncheckedException 이라서
    public static final class MemberLoginException extends RuntimeException{

        public MemberLoginException(String msg){
            super(msg);
        }

    }

    // 로그인
    // 예외처리를 해줘야함! ( 이메일이 틀릴 수도 있고 , pw가 틀릴 수도 있기에!)
    @Override
    public MemberDTO login(String email, String pw) {

        MemberDTO memberDTO = null;

        try {
            // 멤버 데이터 가져오기
            Optional<Member> result = memberRepository.findById(email);

            Member member = result.orElseThrow();

            // 패스워드가 다를 때
            if(member.getPw().equals(pw) == false){
                throw new MemberLoginException("Password Incorrect");
            }

            // DTO타입로 변환
            memberDTO = MemberDTO.builder()
                    .email(member.getEmail())
                    .pw("")
                    .nickname(member.getNickname())
                    // boolean 타입은 isAdmin으로 만들기
                    .admin(member.isAdmin())
                    .build();

        }catch (Exception e){
            // 예외가 나면 MemberLoginExcetion 으로 던짐
            throw new MemberLoginException(e.getMessage());
        }
        return memberDTO;
    }
}