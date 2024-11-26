package umc.moviein.converter;

import org.springframework.stereotype.Component;
import umc.moviein.domain.Movie;
import umc.moviein.web.dto.Movie.MovieDetailDTO;
import umc.moviein.web.dto.Movie.MovieSummaryDTO;

@Component
public class MovieConverter {
    public Movie toEntity(MovieDetailDTO dto) {
        Movie movie = new Movie();
        movie.setMovieCd(dto.getMovieCd());
        movie.setMovieNm(dto.getMovieNm());
        movie.setOpenDt(dto.getOpenDt());
        movie.setRuntime(dto.getRuntime());
        movie.setWatchGradeNm(dto.getWatchGradeNm());
        movie.setDirector(dto.getDirector());
        movie.setActors(dto.getActors()); // 배우 리스트 설정
        movie.setPlot(dto.getPlot()); // 줄거리 추가
        movie.setPosterUrl(dto.getPoster()); // 포스터 URL 추가
        return movie;
    }

    public static MovieDetailDTO toDTO(Movie movie) {
        MovieDetailDTO dto = new MovieDetailDTO();
        dto.setMovieCd(movie.getMovieCd());
        dto.setMovieNm(movie.getMovieNm());
        dto.setOpenDt(movie.getOpenDt());
        dto.setRuntime(movie.getRuntime());
        dto.setWatchGradeNm(movie.getWatchGradeNm());
        dto.setPlot(movie.getPlot());
        dto.setDirector(movie.getDirector());
        dto.setActors(movie.getActors());
        dto.setPoster(movie.getPosterUrl());
        return dto;
    }

    public static MovieSummaryDTO toSummaryDTO(Movie movie) {
        return new MovieSummaryDTO(
                movie.getId(),
                movie.getMovieNm(),
                movie.getPosterUrl()
        );
    }
}
