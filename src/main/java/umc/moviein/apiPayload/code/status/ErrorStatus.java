package umc.moviein.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.moviein.apiPayload.code.BaseErrorCode;
import umc.moviein.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 유저 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자가 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER4002", "사용자가 이미 존재합니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER4003", "닉네임은 필수 입니다."),
    LOGIN_ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER4004", "이미 존재하는 로그인 ID입니다."),

    //영화 관련 에러
    MOVIE_NOT_FOUND(HttpStatus.BAD_REQUEST, "MOVIE4001", "영화가 없습니다."),
    MOVIE_TO_SAVE_NOT_FOUND(HttpStatus.BAD_REQUEST, "MOVIE4002", "저장려는 영화가 없습니다."),
    EXTERNAL_API1_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MOVIE4003", "영화 목록 저장 오류"),
    EXTERNAL_API2_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MOVIE4004", "영화 상세 저장 오류"),

    // 좋아요 및 싫어요 관련 에러
    DUPLICATE_PREFERENCE(HttpStatus.CONFLICT, "PREFERENCE001", "이미 좋아요 혹은 싫어요를 누른 유저입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}