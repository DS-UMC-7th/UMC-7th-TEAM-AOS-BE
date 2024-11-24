package umc.moviein.converter;

import umc.moviein.domain.User;
import umc.moviein.web.dto.sign.*;

public class UserConverter {

    // User Entity -> DTO
    public static SignUpResponseDTO.JoinResultDTO toJoinResultDTO(User user) {
        return SignUpResponseDTO.JoinResultDTO.builder()
                .userId(user.getId())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // DTO -> User Entity
    public static User toUser(SignUpRequestDTO.JoinDto request, String encodedPassword) {
        return User.builder()
                .loginId(request.getLoginId())
                .password(encodedPassword)
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
    }

    // SignInResponseDto 변환 메서드 (로그인 실패 메시지도 추가 처리)
    public static SignInResponseDTO.LoginResultDTO toSignInResponseDto(TokenDTO tokenDto) {
        return SignInResponseDTO.LoginResultDTO.builder()
                .role(tokenDto.getRole())
                .grantType(tokenDto.getGrantType())
                .token(tokenDto.getAccessToken())
                .build();
    }
}

