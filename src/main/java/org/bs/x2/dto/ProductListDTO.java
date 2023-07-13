package org.bs.x2.dto;

import lombok.Data;

@Data
// 제품의 정보를 간소화된 형태로 시스템의 다른 부분으로 전송하기 위해 생성
public class ProductListDTO {

    private Long pno;

    private String pname;

    private int price;

    // 이미지 하나만 가져올거기에
    private String fname;

    // ---------------리뷰 테이블 추가 후 ------------------------

    // 리뷰 개수
    private long reviewCnt;

    // 평점 평균
    private double reviewAvg;
}
