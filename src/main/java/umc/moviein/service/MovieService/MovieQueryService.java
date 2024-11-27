package umc.moviein.service.MovieService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.moviein.domain.Movie;
import umc.moviein.web.dto.Movie.MovieDetailDTO;
import umc.moviein.web.dto.Movie.MovieListDTO;
import umc.moviein.web.dto.Movie.MovieSummaryDTO;

import java.util.List;
import java.util.Map;

public interface MovieQueryService {
    public List<String> generateWeeklyDates(String startDate);
    public List<MovieListDTO> fetchMovies(String targetDate);
    public MovieDetailDTO fetchMovieDetail(String movieCd);
    public void fetchAdditionalMovieDetails(MovieDetailDTO dto);
    public Map<String, Object> getMoviesWithCursorPagination(Long cursor, int limit);
    public MovieDetailDTO getMovieDetailById(Long id);
    List<Movie> getMoviesOrderByLikeWithCursor();
    Page<Movie> getMovieOrderByOpenDateDesc(int page, int limit);
}
