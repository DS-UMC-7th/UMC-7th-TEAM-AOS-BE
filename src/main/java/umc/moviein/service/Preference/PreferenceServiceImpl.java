package umc.moviein.service.Preference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.moviein.apiPayload.Exception.handler.DuplicatePreferenceHandler;
import umc.moviein.apiPayload.Exception.handler.MovieHandler;
import umc.moviein.apiPayload.Exception.handler.UserHandler;
import umc.moviein.apiPayload.code.status.ErrorStatus;
import umc.moviein.domain.Movie;
import umc.moviein.domain.Preference;
import umc.moviein.domain.User;
import umc.moviein.repository.MovieRepository;
import umc.moviein.repository.PreferenceRepository;
import umc.moviein.repository.UserRepository;
import umc.moviein.web.dto.Preference.PreferenceRequestDTO;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService{

    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public Movie createMoviePreference(Integer userId, Long movieId, PreferenceRequestDTO.CreateRequestDTO createPreferenceDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieHandler(ErrorStatus.MOVIE_NOT_FOUND));

        boolean preferenceExists = preferenceRepository.existsByUserAndMovie(user, movie);

        // 이미 좋아요/싫어요를 눌렀다면
        if (preferenceExists) {
            throw new DuplicatePreferenceHandler(ErrorStatus.DUPLICATE_PREFERENCE);
        }

        // setter 사용하지 않기 위해 converter 거치지 않고 service에서 생성함
        Preference preference = Preference.builder()
                .movie(movie)
                .user(user)
                .isLike(createPreferenceDTO.getIsLike())
                .build();

        preferenceRepository.save(preference);

        return movie;
    }
}
