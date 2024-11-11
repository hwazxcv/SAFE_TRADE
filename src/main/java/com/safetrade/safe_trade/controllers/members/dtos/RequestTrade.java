package com.safetrade.safe_trade.controllers.members.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class RequestTrade { // 커맨드 객체 (html양식 만든 후) -> MypageController의 add쪽으로 가서 추가
    private Long seq;
    private String mode; // 추가인지 수정인지 구분함 -> 다시 form으로 이동해서 에러 연동
    private String gid = UUID.randomUUID().toString();
    @NotBlank
    private String type = "SELL";
    @NotBlank
    private String subject;

    private String content;

    @Min(1)
    private long price;
}
