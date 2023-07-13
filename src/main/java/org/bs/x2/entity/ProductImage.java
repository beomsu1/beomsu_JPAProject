package org.bs.x2.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable // 다른 클래스나 구조체 안에 포함될 수 있는 객체를 만들 수 있다.
public class ProductImage {

    private String fname;

    private int ord;
}
