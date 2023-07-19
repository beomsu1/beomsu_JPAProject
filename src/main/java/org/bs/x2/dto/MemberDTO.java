package org.bs.x2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private String email;

    private String pw;

    private String nickname;

    private boolean admin;

    private String accessToken;

    private String refreshToken;


}
