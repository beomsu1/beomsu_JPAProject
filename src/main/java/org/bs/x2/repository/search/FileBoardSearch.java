package org.bs.x2.repository.search;

import org.bs.x2.dto.FileBoardListDTO;
import org.bs.x2.dto.PageRequestDTO;
import org.bs.x2.dto.PageResponseDTO;

public interface FileBoardSearch {
 
    // 목록
    PageResponseDTO<FileBoardListDTO> list (PageRequestDTO requestDTO);
    
}
