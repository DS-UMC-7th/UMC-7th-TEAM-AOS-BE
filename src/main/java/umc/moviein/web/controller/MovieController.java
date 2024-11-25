package umc.moviein.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.moviein.service.MovieService.MovieCommandService;
import umc.moviein.service.MovieService.MovieQueryService;
import umc.moviein.web.dto.Movie.MovieDetailDTO;
import umc.moviein.web.dto.Movie.MovieListDTO;
import umc.moviein.web.dto.Movie.MovieSummaryDTO;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieQueryService movieQueryService;
    private final MovieCommandService movieCommandService;

    public MovieController(MovieQueryService movieQueryService, MovieCommandService movieCommandService) {
        this.movieQueryService = movieQueryService;
        this.movieCommandService = movieCommandService;
    }

    @PostMapping("/save-weekly") //영화 정보 가져와서 저장하는 API
    public ResponseEntity<String> saveWeeklyMovies(@RequestParam String startDate) {
        // 주간 날짜 생성
        List<String> dates = movieQueryService.generateWeeklyDates(startDate);

        for (String targetDate : dates) {
            // 박스오피스 목록 가져오기
            List<MovieListDTO> movies = movieQueryService.fetchMovies(targetDate);

            for (MovieListDTO movie : movies) {
                // 영화 상세 정보 가져오기
                MovieDetailDTO detail = movieQueryService.fetchMovieDetail(movie.getMovieCd());

                // 중복 확인 및 저장
                movieCommandService.saveMovieIfNotExists(detail);
            }
        }

        return ResponseEntity.ok("Weekly movies saved successfully!");
    }

    @GetMapping
    public ResponseEntity<Page<MovieSummaryDTO>> getMovies(Pageable pageable) {
        Page<MovieSummaryDTO> movieSummaries = movieQueryService.getMoviesWithPagination(pageable);
        return ResponseEntity.ok(movieSummaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDTO> getMovieDetail(@PathVariable Long id) {
        MovieDetailDTO movieDetail = movieQueryService.getMovieDetailById(id);
        return ResponseEntity.ok(movieDetail);
    }
}