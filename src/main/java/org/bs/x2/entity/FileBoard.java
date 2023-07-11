package org.bs.x2.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images")
@Builder
public class FileBoard {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) // autoIncrement
    private Long bno;

    private String title;

    private String content;

    private String writer;

    // 20개를일괄처리
    @BatchSize(size = 20)
    // 관리 주체가 OneToMany 라면 cascade 적용 remove , 같이 삭제 처리 
    @OneToMany(cascade = {CascadeType.PERSIST , CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name="board")
    @Builder.Default
    // new ArrayList로 생성해주는 이유는 엔티티 매니저가 리스트를 가져올 때 마다 새로운 리스트를 뽑아냄
    // 그럴 때 디비랑 엔티티매니저가 관리하는 영속성 컨텍스트의 리스트가 달라서 에러가 남
    private List<FileBoardImage> images = new ArrayList<>();
    
    //이미지 CRUD
    public void addImage(FileBoardImage boardImage){

        // 처음에는 ArrayList의 배열이 비어있으니 0
        boardImage.changeOrd(images.size());

        // 리스트에 추가하기
        images.add(boardImage);
    }
}
