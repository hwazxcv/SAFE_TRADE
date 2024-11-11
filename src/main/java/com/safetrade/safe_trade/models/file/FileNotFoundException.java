package com.safetrade.safe_trade.models.file;

import com.safetrade.safe_trade.commons.Utils;
import com.safetrade.safe_trade.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {

    public FileNotFoundException() {
        super(Utils.getMessage("NotFound.file", "error"), HttpStatus.NOT_FOUND);
    }
}