package org.bs.x2.controller;

import java.util.List;
import java.util.Map;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.dto.ProductDTO;
import org.bs.x2.dto.ProductListDTO;
import org.bs.x2.service.ProductService;
import org.bs.x2.util.FileUploader;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@RequestMapping("/api/products/")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    private final FileUploader uploader;

    // 목록
    @GetMapping("list")
    public PageResponseDTO<ProductListDTO> list (PageRequestDTO requestDTO){

        return service.list(requestDTO);

    }

    // 등록
    @PostMapping("")
    public Map<String,Long> register (ProductDTO productDTO){

        log.info(productDTO);

        // 파일 등록
        List<String> fileNames = 
        uploader.uploadFiles(productDTO.getFiles() , true);
        productDTO.setImages(fileNames);

        Long pno = service.register(productDTO);

        return Map.of("result" , pno);
    }

    // 조회
    @GetMapping("{pno}")
    public ProductDTO getOne(@PathVariable("pno") Long pno){

        return service.readOne(pno);
    }

    // 삭제
    @DeleteMapping("{pno}")
    public Map<String,Long> delete (@PathVariable ("pno") Long pno){

        service.delete(pno);

        return Map.of("result" , pno );

    }

    // 수정 put으로 사용 불가 -> post 사용
    @PostMapping("modify")
    public Map<String,Long> modify (ProductDTO productDTO){

        log.info("---------------");
        log.info(productDTO);
        log.info("---------------");

        if(productDTO.getFiles() != null && productDTO.getFiles().size() > 0){

            // productDTO에서 가져온 파일들을 uploader를 사용하여 업로드하고,
            // 업로드된 파일들의 원본 파일 이름들을 uploadFileNames 리스트에 저장
            List<String> uploadFileNames = uploader.uploadFiles(productDTO.getFiles(), true);

            // 기존 이미지
            List<String> oldFileNames = productDTO.getImages();

            // 기존 이미지를 새로운 이미지에 추가해줘야함
            uploadFileNames.forEach(fname -> oldFileNames.add(fname));

        }

        log.info("----------------");
        log.info(productDTO);

        service.modify(productDTO);

        return Map.of("result" , 111L);
    }

}
