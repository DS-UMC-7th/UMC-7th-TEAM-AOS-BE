package umc.moviein.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.moviein.domain.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByMovieCd(String movieCd);
    List<Movie> findByIdGreaterThan(Long cursor, Pageable pageable);
}

