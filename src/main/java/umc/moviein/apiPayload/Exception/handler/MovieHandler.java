package umc.moviein.apiPayload.Exception.handler;

import umc.moviein.apiPayload.Exception.GeneralException;
import umc.moviein.apiPayload.code.BaseErrorCode;

public class MovieHandler extends GeneralException {
    public MovieHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
