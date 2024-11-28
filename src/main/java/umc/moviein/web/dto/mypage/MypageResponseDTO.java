package umc.moviein.web.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.moviein.web.dto.ReviewResponseDto;

import java.util.List;

public class MypageResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageResultDTO{
        private String nickname;
        private String email;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserReviewsDTO {
        private Long userId;
        private String nickname;
        private List<ReviewResponseDto> reviews;
    }
}
