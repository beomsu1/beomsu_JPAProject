package org.bs.x2.service;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.dto.ProductDTO;
import org.bs.x2.dto.ProductListDTO;
import org.bs.x2.entity.Product;
import org.bs.x2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    // 목록
    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {

        return repository.list(requestDTO);

    }

    // 등록
    @Override
    public Long register(ProductDTO productDTO) {

        // 상품 객체 추가
        Product product = Product.builder()
                .pdesc(productDTO.getPdesc())
                .pname(productDTO.getPname())
                .price(productDTO.getPrice())
                .build();

        // 이미지 추가
        // productDTO 안에 있는 getImages를 가져와서 getImages는 List<String> 타입이니까
        // -> fname 파일명만 가져와서 그 파일명으로 addImages
        productDTO.getImages().forEach(fname -> {
            product.addImage(fname);
        });

        // 반환타입이 Long타입이라 .getPno()를 사용해줘야함
        return repository.save(product).getPno();
    }

    // 조회
    @Override
    public ProductDTO readOne(Long pno) {

        Product product = repository.selectOne(pno);

        ProductDTO dto = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                // images는 문자열타입
                .images(product.getImages().stream().map(pi-> pi.getFname()).collect(Collectors.toList()))
                .build();

        return dto;
    }

}
