package umc.moviein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.moviein.domain.Movie;
public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByMovieCd(String movieCd);
}

