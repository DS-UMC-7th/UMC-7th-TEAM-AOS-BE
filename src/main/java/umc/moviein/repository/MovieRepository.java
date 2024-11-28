package umc.moviein.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.moviein.domain.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByMovieCd(String movieCd);
    List<Movie> findByIdGreaterThan(Long cursor, Pageable pageable);

    @Query("SELECT DISTINCT p.movie, p.createdAt FROM Preference p WHERE p.isLike = true ORDER BY p.createdAt DESC")
    List<Movie> findMoviesByLatestLikes();

    Page<Movie> findAllByOrderByOpenDtDesc(PageRequest of);

    @Query("SELECT m FROM Movie m " +
            "LEFT JOIN Review r ON m.id = r.movieId " +
            "GROUP BY m.id " +
            "ORDER BY AVG(r.rating) DESC")
    Page<Movie> findMoviesOrderByAverageRatingDesc(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.movieNm) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.director) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Movie> searchMoviesByKeyword(@Param("keyword") String keyword, Pageable pageable);
}

