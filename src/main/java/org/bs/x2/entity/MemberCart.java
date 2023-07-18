package org.bs.x2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
// 인덱스 email , cno 생성
@Table(name = "member_cart" , indexes = @Index(columnList = "email,cno"))
public class MemberCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    private String email;

    private Long pno;


}
