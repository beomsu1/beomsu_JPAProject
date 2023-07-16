package org.bs.x2.service;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.dto.ProductDTO;
import org.bs.x2.dto.ProductListDTO;
import org.bs.x2.entity.Product;
import org.bs.x2.repository.ProductRepository;
import org.bs.x2.util.FileUploader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    private final FileUploader fileUploader;

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

    // 삭제
    @Override
    public void delete(Long pno) {

        // 조회
        Product product = repository.selectOne(pno);

        // 삭제 여부를 true로 변경
        product.changeDel(true);

        // save
        repository.save(product);

        // 이미지 조회
        List<String> fileNames = product.getImages().stream().map(i -> i.getFname()).collect(Collectors.toList());

        // 파일 삭제
        fileUploader.delete(fileNames);

    }

    // 수정
    @Override
    public void modify(ProductDTO productDTO) {

        // 조회
        Optional<Product> result = repository.findById(productDTO.getPno());

        Product product = result.orElseThrow();

        // 기본 정보들 수정
        product.changePname(productDTO.getPname());
        product.changePdesc(productDTO.getPname());
        product.changePrice(productDTO.getPrice());

        // 기존 이미지 목록 살리기
        List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

        // 이미지들 clear
        product.ClearImage();

        // 이미지 문자열들 추가 addImage()
        // productDTO에서 가져온 이미지를 product에 추가하는 로직
        productDTO.getImages().forEach(fname -> product.addImage(fname));

        log.info(product);

        repository.save(product);

        // 기존 파일들 중에 ProductDTO.getImages에 없는 파일 찾기

        List<String> newFiles = productDTO.getImages();

        // 삭제 대상 파일
        // index에서 -1이면 존재하지 않는 파일이다!

        List<String> wantDeleteFiles =
        oldFileNames.stream().filter(f -> newFiles.indexOf(f) == -1 ).collect(Collectors.toList());

        fileUploader.delete(wantDeleteFiles);
    }

}
