package umc.moviein.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.moviein.domain.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findAllByMovieId(Long movieId);

    Page<Review> findByUserId(Integer userId, Pageable pageable);
}
