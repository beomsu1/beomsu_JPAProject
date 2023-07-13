package org.bs.x2.repository;

import java.util.Optional;
import java.util.UUID;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.dto.ProductListDTO;
import org.bs.x2.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    // insert
    @Test
    public void testInsert() {

        for (int i = 0; i < 100; i++) {

            // 객체 생성하기
            Product product = Product.builder()
                .pname("Test")
                .pdesc("Test")
                .price(4000)
                .build();

            // 임의의 이미지 추가
            product.addImage(UUID.randomUUID().toString() + "_aaa.jpg");
            product.addImage(UUID.randomUUID().toString() + "_bbb.jpg");
            product.addImage(UUID.randomUUID().toString() + "_ccc.jpg");

            // save
            repository.save(product);

        }

    }

    // 조회
    @Test
    public void testRead() {

        // pno 1번 조회
        Product product = repository.selectOne(1L);

        log.info(product);
        log.info("-----------");
        log.info(product.getImages());

    }

    // 삭제
    @Test
    public void testDelete() {

        repository.deleteById(1L);

    }

    // 수정
    @Test
    @Transactional
    public void testUpdate() {

        // 상품 조회
        // Product product = repository.selectOne(2L);

        Optional<Product> result = repository.findById(2L);

        // 예외가 나올 때 던지기
        Product product = result.orElseThrow();

        // Product에서 만든 가격 변경 함수를 이용해서 가격 변경하기
        product.changePrice(4000);

        // 이미지 클리어
        product.ClearImage();

        // 새로운 이미지 생성
        product.addImage(UUID.randomUUID().toString() + "_newImage.jpg");

        // save
        repository.save(product);

    }

    // 목록 조회
    @Test
    public void testList(){

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListDTO> result = repository.list(requestDTO);

        for (ProductListDTO dto : result.getDtoList()) {

            log.info(dto);
            
        }

    }

    // 목록 + 리뷰 조회
    @Test
    public void testList2(){

    PageRequestDTO requestDTO = new PageRequestDTO();

    PageResponseDTO<ProductListDTO> result = repository.listWithReview(requestDTO);

    for (ProductListDTO dto : result.getDtoList()) {
        log.info(dto);
    }

    }

   

    

}
