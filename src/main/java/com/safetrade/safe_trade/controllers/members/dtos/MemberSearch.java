package com.safetrade.safe_trade.controllers.members.dtos;

import lombok.Data;

@Data
public class MemberSearch {
    private int page = 1;
    private int limit = 20;
}
