package umc.moviein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.moviein.domain.Movie;
import umc.moviein.domain.Preference;
import umc.moviein.domain.User;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    boolean existsByUserAndMovie(User user, Movie movie);
}
