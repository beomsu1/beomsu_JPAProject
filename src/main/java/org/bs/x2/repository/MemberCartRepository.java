package org.bs.x2.repository;

import org.bs.x2.dto.MemberCartDTO;
import org.bs.x2.entity.MemberCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCartRepository extends JpaRepository<MemberCart,Long> {

    // 카트 조회
    @Query("select mc from MemberCart mc where mc.email = :email order by mc.cno asc")
    List<MemberCart> selectCart (@Param("email") String email);
}
