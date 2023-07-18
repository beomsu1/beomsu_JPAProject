package org.bs.x2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bs.x2.dto.MemberCartDTO;
import org.bs.x2.entity.MemberCart;
import org.bs.x2.repository.MemberCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberCartServiceImpl implements MemberCartService{

    private final MemberCartRepository cartRepository;

    // 카트 추가
    @Override
    public List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO) {

        //entity 뽑아내기
        MemberCart cart = dtoToEntity(memberCartDTO);

        cartRepository.save(cart);

        // 카트 선택 - Entity들이 나옴
        List<MemberCart> cartList = cartRepository.selectCart(memberCartDTO.getEmail());

        log.info(cartList);

        return cartList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }

    // 카트 가져오기
    @Override
    public List<MemberCartDTO> getCart(String email) {

        // 카트 선택
        List<MemberCart> cartList = cartRepository.selectCart(email);

        // entity를 DTO타입의 리스트로 만들어서 리턴
        return cartList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }
}
