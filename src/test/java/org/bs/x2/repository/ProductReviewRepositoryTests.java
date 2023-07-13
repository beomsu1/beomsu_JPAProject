package org.bs.x2.repository;

import org.bs.x2.entity.Product;
import org.bs.x2.entity.ProductReview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductReviewRepositoryTests {
    
    @Autowired
    private ProductReviewRepository repository;

    // 리뷰 등록
    @Test
    public void insertReview(){

        // 여러개 한번에 등록하기위해 배열사용
        Long[] pnoArr = {200L , 195L , 192L , 188L , 181L};

        for (Long pno : pnoArr) {
            
            int score = (int)(Math.random() *5)+1;

            // 상품 생성
            Product product = Product.builder().build();

            for (int i = 0; i < 10; i++) {
                
                ProductReview review = ProductReview.builder()
                .content("cdaskdascxz")
                .reviewer("beomsu")
                .score(score)
                .product(product)
                .build();

                repository.save(review);
            } // end for
        }
    }
}
