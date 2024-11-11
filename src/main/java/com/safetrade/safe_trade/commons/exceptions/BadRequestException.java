package com.safetrade.safe_trade.commons.exceptions;


import com.safetrade.safe_trade.commons.Utils;

public class BadRequestException extends AlertBackException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super(Utils.getMessage("BadRequest", "error"));
    }
}