package umc.moviein.web.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    // 토큰을 생성할 때 사용되는 dto
    private String grantType;
    private String accessToken;
    private String role;
    private long accessTokenExpiresIn;
    private String refreshToken;
}