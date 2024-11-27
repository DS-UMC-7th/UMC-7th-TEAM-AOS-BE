package umc.moviein.converter;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.moviein.domain.Movie;
import umc.moviein.domain.Preference;
import umc.moviein.web.dto.Movie.MovieDetailDTO;
import umc.moviein.web.dto.Movie.MovieResponseDTO;
import umc.moviein.web.dto.Movie.MovieSummaryDTO;

import java.util.List;

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
        List<Preference> preferences = movie.getPreferences();

        Long likeCount = preferences.stream().filter(Preference::isLike).count();
        Long dislikeCount = preferences.stream().filter(preference -> !preference.isLike()).count();
        Integer totalCount = preferences.size();

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
        dto.setLikeCount(likeCount);
        dto.setDislikeCount(dislikeCount);
        dto.setTotalCount(totalCount);
        return dto;
    }

    public static MovieSummaryDTO toSummaryDTO(Movie movie) {
        return new MovieSummaryDTO(
                movie.getId(),
                movie.getMovieNm(),
                movie.getPosterUrl()
        );
    }

    public static MovieResponseDTO.GetMovieListResponseDTO toGetMovieListResponseDTO (List<Movie> movies) {

        List<MovieSummaryDTO> movieSummaryDTOList = movies.stream()
                .map(MovieConverter::toSummaryDTO).toList();

        return MovieResponseDTO.GetMovieListResponseDTO.builder()
                .movieList(movieSummaryDTOList)
                .build();
    }

    public static MovieResponseDTO.GetMovieListWithPageResponseDTO toGetMovieListWithPageResponseDTO(Page<Movie> moviePage) {
        List<MovieSummaryDTO> movieSummaryDTOList = moviePage.stream()
                .map(MovieConverter::toSummaryDTO).toList();

        return MovieResponseDTO.GetMovieListWithPageResponseDTO.builder()
                .isFirst(moviePage.isFirst())
                .isLast(moviePage.isLast())
                .totalPage(moviePage.getTotalPages())
                .totalElements(moviePage.getTotalElements())
                .listSize(movieSummaryDTOList.size())
                .movieList(movieSummaryDTOList)
                .build();
    }
}
