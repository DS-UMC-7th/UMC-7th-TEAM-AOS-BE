package umc.moviein.web.dto.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


public class SignUpRequestDTO {
    @Getter
    public static class JoinDto{
        @Schema(description = "로그인 시 사용되는 Id")
        String loginId;
        String password;
        String email;
        String nickname;
    }

}
