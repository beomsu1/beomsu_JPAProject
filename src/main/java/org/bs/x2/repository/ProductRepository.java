package org.bs.x2.repository;

import org.bs.x2.entity.Product;
import org.bs.x2.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> , ProductSearch{

    // 조회
    //images는 같이 로딩해와라! 라고 지시
    @EntityGraph(attributePaths = "images")
    @Query("select p from Product p where p.delFlag=false and p.pno = :pno ")
    Product selectOne (@Param("pno") Long pno);
}
