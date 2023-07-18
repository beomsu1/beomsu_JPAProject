package org.bs.x2.service;

import jakarta.transaction.Transactional;
import org.bs.x2.dto.MemberDTO;

@Transactional
public interface MemberService {

    // 로그인 기능
    MemberDTO login ( String email , String pw);


}
