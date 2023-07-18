package org.bs.x2.service;

import jakarta.transaction.Transactional;
import org.bs.x2.dto.MemberCartDTO;
import org.bs.x2.entity.MemberCart;

import java.util.List;

@Transactional
public interface MemberCartService {

    // 카트 추가
    List<MemberCartDTO> addCart (MemberCartDTO memberCartDTO);

    // dto -> entity 로 변환
    default MemberCart dtoToEntity(MemberCartDTO dto){
        MemberCart entity = MemberCart.builder()
                .cno(dto.getCno())
                .email(dto.getEmail())
                .pno(dto.getPno())
                .build();

        return entity;
    }

    // entity -> dto로 변환
    default MemberCartDTO entityToDTO(MemberCart entity){
        MemberCartDTO dto = MemberCartDTO.builder()
                .cno(entity.getCno())
                .email(entity.getEmail())
                .pno(entity.getPno())
                .build();

        return dto;
    }

    //카트 가져오기
    List<MemberCartDTO> getCart (String email);


}
