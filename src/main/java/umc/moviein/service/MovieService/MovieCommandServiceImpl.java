package umc.moviein.service.MovieService;

import org.springframework.stereotype.Service;
import umc.moviein.domain.Movie;
import umc.moviein.repository.MovieRepository;
import umc.moviein.converter.MovieConverter;
import umc.moviein.web.dto.Movie.MovieDetailDTO;

@Service
public class MovieCommandServiceImpl implements MovieCommandService {
    private final MovieRepository movieRepository;
    private final MovieConverter movieConverter;
    private final MovieQueryService movieQueryService;

    public MovieCommandServiceImpl(MovieRepository movieRepository, MovieConverter movieConverter, MovieQueryService movieQueryService) {
        this.movieRepository = movieRepository;
        this.movieConverter = movieConverter;
        this.movieQueryService = movieQueryService;
    }

    @Override
    public void saveMovieIfNotExists(MovieDetailDTO dto) {
        //movieCd를 기준 중복 확인
        boolean exists = movieRepository.existsByMovieCd(dto.getMovieCd());
        if (!exists) {
            // KMDB API에서 추가 정보 가져오기
            movieQueryService.fetchAdditionalMovieDetails(dto); // 줄거리와 포스터 추가

            var movie = movieConverter.toEntity(dto);
            movieRepository.save(movie);
        }
    }
}