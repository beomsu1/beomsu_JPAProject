package org.bs.x2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Member {

    @Id
    private String email;

    @JsonIgnore
    private String pw;

    private String nickname;

    private boolean admin;
}
