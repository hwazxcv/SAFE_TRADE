package com.safetrade.safe_trade.entities;

import com.safetrade.safe_trade.commons.constants.MemberType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Member extends Base {

    @Id @GeneratedValue
    private Long userNo;

    @Column(length=65, nullable = false, unique = true)
    private String email;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=30, nullable = false)
    private String userNm;

    @Column(length=11)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private MemberType mtype = MemberType.USER;

}
