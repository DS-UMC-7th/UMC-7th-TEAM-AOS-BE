package umc.moviein.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.moviein.domain.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByMovieCd(String movieCd);
    List<Movie> findByIdGreaterThan(Long cursor, Pageable pageable);

    @Query("SELECT DISTINCT p.movie, p.createdAt FROM Preference p WHERE p.isLike = true ORDER BY p.createdAt DESC")
    List<Movie> findMoviesByLatestLikes();

    Page<Movie> findAllByOrderByOpenDtDesc(PageRequest of);
}

