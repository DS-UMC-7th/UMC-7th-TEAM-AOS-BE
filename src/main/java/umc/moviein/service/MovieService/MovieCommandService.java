package umc.moviein.service.MovieService;

import umc.moviein.domain.Movie;
import umc.moviein.web.dto.Movie.MovieDetailDTO;

public interface MovieCommandService {
    public void saveMovieIfNotExists(MovieDetailDTO dto);
    public Movie saveMovie(Movie movie);
}
