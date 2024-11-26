package umc.moviein.web.dto.Preference;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PreferenceRequestDTO {
    @Getter
    public static class CreateRequestDTO {
        @NotNull(message = "isLike 필드는 반드시 값이 있어야 합니다.")
        private Boolean isLike;
    }
}
