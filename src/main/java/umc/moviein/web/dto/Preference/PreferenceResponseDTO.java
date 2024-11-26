package umc.moviein.web.dto.Preference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PreferenceResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResponseDTO {
        private Long movieId;
        private String movieNm;
        private Long likeCount;
        private Long dislikeCount;
        private Integer totalCount;
    }
}
