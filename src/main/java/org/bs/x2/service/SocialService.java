package org.bs.x2.service;

import jakarta.transaction.Transactional;

@Transactional
public interface SocialService {

    // 이메일 가져오기
    String getKakaoEmail (String email);
}
