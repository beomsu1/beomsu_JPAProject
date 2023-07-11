package org.bs.x2.repository;

import java.util.Optional;
import java.util.UUID;

import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.entity.FileBoard;
import org.bs.x2.entity.FileBoardImage;
import org.bs.x2.repository.FileBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class FileBoardRepositoryTests {

    @Autowired
    private FileBoardRepository repository;

    // insert
    @Test
    public void insert(){
        for (int i = 0; i < 100; i++) {
            
            FileBoard fileBoard = FileBoard.builder()
        .title("AA")
        .content("AAA")
        .writer("Beomsu")
        .build();

        FileBoardImage img1 = FileBoardImage.builder()
        .uuid(UUID.randomUUID().toString())
        .fname("aaa.jpg")
        .build();

        FileBoardImage img2 = FileBoardImage.builder()
        .uuid(UUID.randomUUID().toString())
        .fname("bbb.jpg")
        .build();

        fileBoard.addImage(img1);
        fileBoard.addImage(img2);

        repository.save(fileBoard);

    }
        }
        

    // 삭제
    @Test
    @Transactional
    @Commit
    public void delete(){
    
        Long bno = 2L;

        repository.deleteById(bno);

    }

    // 조회
    @Test
    @Transactional
    public void read(){

        Long bno = 99L;

        Optional<FileBoard> result = repository.findById(bno);

        FileBoard fileBoard = result.orElseThrow();

        log.info(fileBoard);
    }

    // 목록
    @Test
    @Transactional
    public void list(){

        Pageable pageable = PageRequest.of(0, 10);

        Page<FileBoard> result = repository.findAll(pageable);

        // log.info(result);

        result.get().forEach(board -> {log.info(board); log.info(board.getImages());
        });
    }

    // Querydsl을 사용한 목록
    @Test
    @Transactional
    public void listQuerydsl(){

        // 페이지 요청에 대한 정보를 담는 PageRequsetDTO 객체를 생성하고 이를 requestDTO 변수에 할당
        PageRequestDTO requestDTO = new PageRequestDTO();

        log.info(repository.list(requestDTO));
    }

    // 한개 값 조회
    @Test
    //  @Transactional -> EntityGragh를 사용해도 됨
    public void selectOne(){

        Long bno = 99L;

        FileBoard board = repository.selectOne(bno);

        log.info(board);
        log.info(board.getImages());
    }
    
}
