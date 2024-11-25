package umc.moviein.web.dto.Movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
//저장한 영화 리스트를 불러오기 위한 DTO
@Getter
@AllArgsConstructor
public class MovieSummaryDTO {
    private Long id;
    private String title;
    private String posterUrl;
}
