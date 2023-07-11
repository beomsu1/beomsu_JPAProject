package org.bs.x2.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

@Data
// 재사용하게 제네릭타입으로 생성하자
// 클라이언트쪽으로 무엇을 보낼지 생각해서 변수를 잡자!
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private long totalCount;

    private List<Integer> pageNums;

    private boolean prev, next;

    // 페이지 요청 정보를 저장하면서 응답 데이터와 함께 클라이언트에게 필요한 정보를 주기 위해서 필드 선언
    private PageRequestDTO requestDTO;

    private int page , size , start , end;

    // 데이터 , 전체 개수 , 페이지 요청 정보 초기화하는 생성자 생성
    public PageResponseDTO(List<E> dtoList , long totalCount , PageRequestDTO pageRequestDTO){
        this.dtoList=dtoList;
        this.totalCount=totalCount;
        this.requestDTO=pageRequestDTO;

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        // 범위에 해당하는 마지막 페이지 번호 구하는 방법
        int tempEnd =(int)(Math.ceil(page/10.0)*10);

        // 시작 페이지 번호
        this.start = tempEnd-9;

        // start가 1이 아닐때 prev 활성화
        this.prev = start != 1;

        // 총 페이지 번호
        int realEnd = (int)(Math.ceil(totalCount/(double)size));

        // 범위에 해당하는 마지막 페이지 번호 저장
        this.end = tempEnd > realEnd ? realEnd : tempEnd;

        // 다음 페이지로 넘어가는 로직
        this.next = (this.end * this.size) < totalCount;

        // start와 end 까지의 범위를 계산해서 pageNums 생성
        // boxed() -> IntStream을 Stream<Integer> 타입으로 변경
        this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();

    }


    
}
