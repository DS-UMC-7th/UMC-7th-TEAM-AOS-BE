package umc.moviein.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.apiPayload.Exception.handler.MovieHandler;
import umc.moviein.apiPayload.code.status.ErrorStatus;
import umc.moviein.converter.MovieConverter;
import umc.moviein.domain.Movie;
import umc.moviein.service.MovieService.MovieCommandService;
import umc.moviein.service.MovieService.MovieQueryService;
import umc.moviein.web.dto.Movie.MovieDetailDTO;
import umc.moviein.web.dto.Movie.MovieResponseDTO;
import umc.moviein.web.dto.Movie.MovieSummaryDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "영화 API", description = "영화 조회 관련 API입니다.")
public class MovieController {
    private final MovieQueryService movieQueryService;
    private final MovieCommandService movieCommandService;

    public MovieController(MovieQueryService movieQueryService, MovieCommandService movieCommandService) {
        this.movieQueryService = movieQueryService;
        this.movieCommandService = movieCommandService;
    }

    @GetMapping("/save") // 영화 정보 가져와서 저장하는 API
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

    @GetMapping("/like-order")
    @Operation(summary = "최근 좋아요 눌린 영화 조회",description = "최근 좋아요 눌린 영화를 조회합니다.")
    public ApiResponse<MovieResponseDTO.GetMovieListResponseDTO> getMoviesOrderByLikeWithCursor () {
        List<Movie> movies = movieQueryService.getMoviesOrderByLikeWithCursor();
        return ApiResponse.onSuccess(MovieConverter.toGetMovieListResponseDTO(movies));
    }

    @GetMapping("/order-open-date")
    @Operation(summary = "개봉일 역순 조회",description = "개봉일 역순으로 영화를 조회합니다. page는 가져올 페이지, size는 한번에 가져올 양입니다.")
    public ApiResponse<MovieResponseDTO.GetMovieListWithPageResponseDTO> getMovieOrderByOpenDateDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size // 가져올 데이터 개수
    ) {
        Page<Movie> movies = movieQueryService.getMovieOrderByOpenDateDesc(page, size);
        return ApiResponse.onSuccess(MovieConverter.toGetMovieListWithPageResponseDTO(movies));

    }

    @GetMapping("/order-rate")
    @Operation(summary = "평점 순 조회",description = "평점이 높은 영화 순으로 조회합니다. page는 가져올 페이지, size는 한번에 가져올 양입니다.")
    public ApiResponse<MovieResponseDTO.GetMovieListWithPageResponseDTO> getMovieOrderByReviewRate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size // 가져올 데이터 개수
    ) {
        Page<Movie> movies = movieQueryService.getMovieOrderByReviewRate(page, size);
        return ApiResponse.onSuccess(MovieConverter.toGetMovieListWithPageResponseDTO(movies));
    }

    @GetMapping("/search")
    @Operation(summary = "영화 검색", description = "키워드로 영화를 검색합니다. 페이지 정보를 포함하여 반환합니다.")
    public ApiResponse<Page<MovieSummaryDTO>> searchMovies(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        // 영화 검색 결과를 가져옴
        Page<Movie> movies = movieQueryService.searchMoviesByKeyword(keyword, page, size);

        // 검색 결과가 없을 때
        if (movies.isEmpty()) {
            throw new MovieHandler(ErrorStatus.MOVIE_NOT_FOUND);
        }

        // Movie를 MovieSummaryDTO로 변환
        Page<MovieSummaryDTO> response = movies.map(MovieConverter::toSummaryDTO);

        // 검색 결과가 있을 때 성공 응답
        return ApiResponse.onSuccess(response);
    }
}