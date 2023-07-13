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

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY) // 연관 관계는 LAZY 걸고 시작
    private List<ProductImage> images = new ArrayList<>();

    public void addImage(String name){

        // 이미지라는 객체 생성
        ProductImage pImage = ProductImage.builder()
                .fname(name)
                .ord(images.size())
                .build();

        images.add(pImage);
    }

    // 이미지 비우기
    public void ClearImage(){
        images.clear();
    }

    // 가격 변경
    public void changePrice(int price){
        this.price = price;
    }

}