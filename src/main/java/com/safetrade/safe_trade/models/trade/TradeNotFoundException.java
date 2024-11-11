package com.safetrade.safe_trade.models.trade;

import com.safetrade.safe_trade.commons.Utils;
import com.safetrade.safe_trade.commons.exceptions.AlertException;
import org.springframework.http.HttpStatus;

public class TradeNotFoundException extends AlertException {


    public TradeNotFoundException() {
        super(Utils.getMessage("NotFound.board", "error"));
        setStatus(HttpStatus.NOT_FOUND);
    }
}
