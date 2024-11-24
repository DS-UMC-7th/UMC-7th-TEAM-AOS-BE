package umc.moviein.apiPayload.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import umc.moviein.apiPayload.code.BaseErrorCode;
import umc.moviein.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}