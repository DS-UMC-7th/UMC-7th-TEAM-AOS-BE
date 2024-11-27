package umc.moviein.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfoResponseDto {
    private Long movieId;
    private List<ReviewResponseDto> reviews;
}
