package umc.moviein.service.Preference;

import umc.moviein.domain.Movie;
import umc.moviein.web.dto.Preference.PreferenceRequestDTO;

public interface PreferenceService {
    Movie createMoviePreference(Integer userId, Long movieId, PreferenceRequestDTO.CreateRequestDTO createPreferenceDTO);
}
