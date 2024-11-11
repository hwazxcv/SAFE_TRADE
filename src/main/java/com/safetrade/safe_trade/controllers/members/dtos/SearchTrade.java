package com.safetrade.safe_trade.controllers.members.dtos;

import lombok.Data;

@Data
public class SearchTrade {
    private int page = 1;
    private int limit = 20;
    private String keyword;
    private String mode;
}
