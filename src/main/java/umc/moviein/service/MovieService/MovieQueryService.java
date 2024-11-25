package umc.moviein.service.MovieService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.moviein.web.dto.Movie.MovieDetailDTO;
import umc.moviein.web.dto.Movie.MovieListDTO;
import umc.moviein.web.dto.Movie.MovieSummaryDTO;

import java.util.List;

public interface MovieQueryService {
    public List<String> generateWeeklyDates(String startDate);
    public List<MovieListDTO> fetchMovies(String targetDate);
    public MovieDetailDTO fetchMovieDetail(String movieCd);
    public void fetchAdditionalMovieDetails(MovieDetailDTO dto);
    public Page<MovieSummaryDTO> getMoviesWithPagination(Pageable pageable);
    public MovieDetailDTO getMovieDetailById(Long id);

}
