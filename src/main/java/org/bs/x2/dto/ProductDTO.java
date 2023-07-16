package org.bs.x2.dto;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// entity 와 변수를 똑같이 만들자
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDTO {
    
    private Long pno;

    private String pname;

    private String pdesc;

    private int price;

    // 문자열 타입의 이미지
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();


}
