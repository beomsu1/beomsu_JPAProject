package org.bs.x2.repository.search;

import java.util.List;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.dto.ProductListDTO;
import org.bs.x2.entity.Product;
import org.bs.x2.entity.QProduct;
import org.bs.x2.entity.QProductImage;
import org.bs.x2.entity.QProductReview;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch{

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {

        QProduct product = QProduct.product;
        QProductImage pImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);

        query.leftJoin(product.images, pImage);

        // 0번째 이미지
        query.where(pImage.ord.eq(0));

        // pageable page의 값
        // pageable 의 기본 page 값은 0 이기에
        int pageNum = requestDTO.getPage() <= 0 ? 0 : requestDTO.getPage()-1;

        // pageable 생성
        Pageable pageable = PageRequest.of(pageNum, requestDTO.getSize(), Sort.by("pno").descending());

        // 쿼리를 pageable로 페이징처리
        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ProductListDTO> dtoQuery = 
        query.select(Projections.bean(ProductListDTO.class, product.pno,product.pname,product.price,pImage.fname));

        //dtoQuery를 fetch 사용해서 List형식으로 반환
        List<ProductListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, requestDTO);


    }

    @Override
    public PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO requestDTO) {

        QProduct product = QProduct.product;
        QProductImage pImage = QProductImage.productImage;
        QProductReview review = QProductReview.productReview;

        JPQLQuery<Product> query = from(product);

        query.leftJoin(product.images, pImage);
        query.leftJoin(review).on(review.product.eq(product));

        query.where(pImage.ord.eq(0));
        query.where(product.delFlag.eq(Boolean.FALSE));

        int PageNum = requestDTO.getPage() < 0 ? 0 : requestDTO.getPage()-1;

        Pageable pageable = PageRequest.of(PageNum, requestDTO.getSize(), Sort.by("pno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        query.groupBy(product);

        JPQLQuery<ProductListDTO> dtoQuery =
        query.select(Projections.bean(ProductListDTO.class,
        product.pno,
        product.pname,
        product.price,
        pImage.fname.min().as("fname"),
        review.score.avg().as("reviewAvg"),
        review.count().as("reviewCnt")
        ));

        List<ProductListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, requestDTO);

    }
    
}
