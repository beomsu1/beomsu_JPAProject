package org.bs.x2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileBoardListDTO {
    
    private Long bno;
    private String title;
    private String content;
    private String fname;

}
