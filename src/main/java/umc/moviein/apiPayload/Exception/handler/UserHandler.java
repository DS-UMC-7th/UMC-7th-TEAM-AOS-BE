package umc.moviein.apiPayload.Exception.handler;

import umc.moviein.apiPayload.Exception.GeneralException;
import umc.moviein.apiPayload.code.BaseErrorCode;

public class UserHandler extends GeneralException {

    public UserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
