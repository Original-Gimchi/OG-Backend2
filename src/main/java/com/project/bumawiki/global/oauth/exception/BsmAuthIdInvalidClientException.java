package com.project.bumawiki.global.oauth.exception;

import com.project.bumawiki.global.error.exception.BumawikiException;
import com.project.bumawiki.global.error.exception.ErrorCode;

public class BsmAuthIdInvalidClientException extends BumawikiException {

    public static final BsmAuthIdInvalidClientException EXCEPTION = new BsmAuthIdInvalidClientException(ErrorCode.BSM_AUTH_INVALID_CLIENT);

    public BsmAuthIdInvalidClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
