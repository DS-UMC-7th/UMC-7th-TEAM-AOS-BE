package umc.moviein.apiPayload.Exception.handler;

import umc.moviein.apiPayload.Exception.GeneralException;
import umc.moviein.apiPayload.code.BaseErrorCode;

public class DuplicatePreferenceHandler extends GeneralException {
    public DuplicatePreferenceHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
