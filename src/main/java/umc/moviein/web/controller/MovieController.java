package umc.moviein.web.controller;

import org.springframework.web.bind.annotation.*;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.service.MovieService.MovieCommandService;
import umc.moviein.service.MovieService.MovieQueryService;
import umc.moviein.web.dto.Movie.MovieDetailDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieQueryService movieQueryService;
    private final MovieCommandService movieCommandService;

    public MovieController(MovieQueryService movieQueryService, MovieCommandService movieCommandService) {
        this.movieQueryService = movieQueryService;
        this.movieCommandService = movieCommandService;
    }

    @PostMapping("/save") // 영화 정보 가져와서 저장하는 API
    public ApiResponse<Void> saveWeeklyMovies(@RequestParam String startDate) {
        movieQueryService.generateWeeklyDates(startDate).stream() // 주간 날짜 스트림 생성
                .map(movieQueryService::fetchMovies) // 각 날짜의 영화 목록 가져오기
                .flatMap(List::stream) // 각 영화 목록(List<MovieListDTO>)을 단일 스트림으로 펼치기
                .map(movie -> movieQueryService.fetchMovieDetail(movie.getMovieCd())) // 각 영화의 상세 정보 가져오기
                .forEach(movieCommandService::saveMovieIfNotExists); // 중복 확인 및 저장

        return ApiResponse.onSuccess(null);
    }

    @GetMapping //영화 목록 불러오기
    public ApiResponse<Map<String, Object>> getMoviesWithCursor(
            @RequestParam(required = false) Long cursor, // 커서 값 (null 가능)
            @RequestParam(defaultValue = "20") int limit // 가져올 데이터 개수
    ) {
        Map<String, Object> result = movieQueryService.getMoviesWithCursorPagination(cursor, limit);
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/{id}") //영화 상세 불러오기
    public ApiResponse<MovieDetailDTO> getMovieDetail(@PathVariable Long id) {
        MovieDetailDTO movieDetail = movieQueryService.getMovieDetailById(id);
        return ApiResponse.onSuccess(movieDetail);
    }
}