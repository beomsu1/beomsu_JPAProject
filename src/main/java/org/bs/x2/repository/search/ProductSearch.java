package org.bs.x2.repository.search;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.dto.ProductListDTO;

public interface ProductSearch {

    // 목록
    PageResponseDTO<ProductListDTO> list (PageRequestDTO requestDTO);

    // 리뷰가 product에 포함되어 있어서 ProductSearch에서 검색가능
    // 목록 + review
    PageResponseDTO<ProductListDTO> listWithReview (PageRequestDTO requestDTO);
    
}
