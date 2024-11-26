package umc.moviein.converter;

import lombok.RequiredArgsConstructor;
import umc.moviein.domain.Movie;
import umc.moviein.domain.Preference;
import umc.moviein.web.dto.Preference.PreferenceResponseDTO;

import java.util.List;

public class PreferenceConverter {

    public static PreferenceResponseDTO.CreateResponseDTO toCreateResponseDTO (Movie movie) {
        List<Preference> preferences = movie.getPreferences();

        Long likeCount = preferences.stream().filter(Preference::isLike).count();
        Long dislikeCount = preferences.stream().filter(preference -> !preference.isLike()).count();
        Integer totalCount = preferences.size();

        return PreferenceResponseDTO.CreateResponseDTO.builder()
                .movieId(movie.getId())
                .movieNm(movie.getMovieNm())
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .totalCount(totalCount)
                .build();
    }
}
