package org.bs.x2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "images")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private String pdesc;

    private String writer;

    private int price;

    // 삭제 됐는지 db에서 확인하기 위해
    private boolean delFlag;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY) // 연관 관계는 LAZY 걸고 시작
    private List<ProductImage> images = new ArrayList<>();

    public void addImage(String name) {

        // 이미지라는 객체 생성
        ProductImage pImage = ProductImage.builder()
                .fname(name)
                .ord(images.size())
                .build();

        images.add(pImage);
    }

    // 이미지 비우기
    public void ClearImage() {
        images.clear();
    }

    // 가격 변경
    public void changePrice(int price) {
        this.price = price;
    }


    // 이름 변경
    public void changePname ( String pname){
        this.pname = pname;

    }

    // 설명 변경
    public void changePdesc ( String pdesc){
        this.pdesc = pdesc;
    }

    // 삭제 여부
    public void changeDel ( boolean delFlag){
        this.delFlag = delFlag;
    }

}


