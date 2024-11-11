package com.safetrade.safe_trade.entities;

import com.safetrade.safe_trade.commons.constants.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Trade extends Base { // 하나하나의 데이터가 게시판을 의미(게시판 설정과 게시판 데이터 두가지 구조로 엔티티를 구성)
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid;

    @Enumerated(EnumType.STRING)
    @Column(length=15, nullable = false)
    private TradeType type;

    @Column(length=100, nullable = false)
    private String subject;
    private String content;

    private long price;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="userNo")
    private Member member;

    @Transient
    private List<FileInfo> editorImages;
}
