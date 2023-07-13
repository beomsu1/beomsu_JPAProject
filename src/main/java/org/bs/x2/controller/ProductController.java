package org.bs.x2.controller;

import java.util.List;
import java.util.Map;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.dto.ProductDTO;
import org.bs.x2.dto.ProductListDTO;
import org.bs.x2.service.ProductService;
import org.bs.x2.util.FileUploader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        uploader.uploadFiles(productDTO.getFiles());

        return Map.of("result" , 123L);
    }
    
}
