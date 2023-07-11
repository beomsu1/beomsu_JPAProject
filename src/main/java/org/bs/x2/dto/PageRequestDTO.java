package org.bs.x2.dto;

import lombok.Data;
import lombok.ToString;

// page , size , type , keyword 생성

@Data
@ToString
public class PageRequestDTO {
    
    // 필수적으로 필요한 친구들
    private int page = 1;
    private int size = 10;

    private String type, keyword;

    // 이렇게 많은 생성자를 생성해서 값을 저장하는 이유는 : 다양한 인자 조합에 사용하기 위함
    // 정보에 따라 다른 생성자를 호출하여 객체를 생성 가능 -> 유연성 제공 , 다양한 요청에 대응
    // -> 유연성과 재사용성을 높임

    // 기본적인 페이지 정보 설정
    public PageRequestDTO(){
        this(1,10);
    }

    public PageRequestDTO(int page, int size){
        this(page,size,null,null);
        
    }

    // 제약 조건을 달아서 설정하자
    public PageRequestDTO(int page , int size , String type, String keyword){
        
        // page 제한
        this.page=page<0 ? 1 : page;

        // size 제한
        this.size=size < 0 || size > 100 ? 10 : size ;
        this.type=type;
        this.keyword=keyword;
    }
}
