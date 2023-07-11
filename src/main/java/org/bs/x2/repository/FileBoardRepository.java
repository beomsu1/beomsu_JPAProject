package org.bs.x2.repository;

import org.bs.x2.entity.FileBoard;
import org.bs.x2.repository.search.FileBoardSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileBoardRepository extends JpaRepository<FileBoard,Long> , FileBoardSearch{

    // 한개 값 조회

    @EntityGraph(attributePaths = {"images"})
    @Query("select b from FileBoard b where b.bno = :bno")
    FileBoard selectOne(@Param("bno") Long bno);
    
}
