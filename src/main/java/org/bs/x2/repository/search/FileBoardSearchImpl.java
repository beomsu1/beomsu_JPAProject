package org.bs.x2.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.bs.x2.dto.FileBoardListDTO;
import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;
import org.bs.x2.entity.FileBoard;
import org.bs.x2.entity.QFileBoard;
import org.bs.x2.entity.QFileBoardImage;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FileBoardSearchImpl extends QuerydslRepositorySupport implements FileBoardSearch {

    // 도메인 호출하기
    public FileBoardSearchImpl() {
        super(FileBoard.class); // 도메인 이름을 넣자

    }

    @Override
    public PageResponseDTO<FileBoardListDTO> list(PageRequestDTO requestDTO) {

        // 변수를 통해 Q파일에 접근
        QFileBoard board = QFileBoard.fileBoard;
        QFileBoardImage boardImage = QFileBoardImage.fileBoardImage;

        // 엔티티와 관련된 JPQL 쿼리 실행하기 위해 사용되는 퀴리 객체 참조
        JPQLQuery<FileBoard> query = from(board);

        query.leftJoin(board.images,boardImage);

        // where 절 boardImage의 ord가 0일 떄 -> 대표이미지
        query.where(boardImage.ord.eq(0));

        // pageNum값 조건 설정
        int PageNum = requestDTO.getPage() - 1 < 0 ? 0 : requestDTO.getPage() - 1;

        // 페이징 적용
        Pageable pageable = PageRequest.of(PageNum, requestDTO.getSize(), Sort.by("bno").descending());

        // query를 pagealbe로 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        // Projections.bean 은 주어진 클래스(FileBoardListDTO)를 기반으로 프로젝션을 생성하는 역할
        // board.bno, board.title, boardImage.uuid, boardImage.fname -> FileBoardListDTO
        // 객체의 해당 필드에 매핑
        JPQLQuery<FileBoardListDTO> listQuery = query.select(Projections.bean(FileBoardListDTO.class,
                board.bno, board.title, boardImage.uuid, boardImage.fname));

        // listQuery를 리스트 형태로 반환
        List<FileBoardListDTO> list = listQuery.fetch();

        // 총 개수
        long totalCount = listQuery.fetchCount(); 

        // list , totalCount , requestDTO를 이용해 새로운 PageRepsonseDTO 를 생성
        return new PageResponseDTO<>(list, totalCount, requestDTO);

    }

}