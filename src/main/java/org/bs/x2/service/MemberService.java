package org.bs.x2.service;

import jakarta.transaction.Transactional;
import org.bs.x2.dto.MemberDTO;

@Transactional
public interface MemberService {

    // 로그인 기능
    MemberDTO login ( String email , String pw);

    // 멤버랑 같이 이메일 받기
    MemberDTO getMemberWithEmail(String email);


}
