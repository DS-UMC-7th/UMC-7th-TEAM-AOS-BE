package umc.moviein.web.dto.Movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MovieResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMovieListResponseDTO {
        private List<MovieSummaryDTO> movieList;
    }
}
