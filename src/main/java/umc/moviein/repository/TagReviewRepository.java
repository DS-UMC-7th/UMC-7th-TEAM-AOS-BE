package umc.moviein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.moviein.domain.Tag;
import umc.moviein.domain.mapping.TagReview;

import java.util.List;

@Repository
public interface TagReviewRepository extends JpaRepository<TagReview, Long> {

    @Query("SELECT t FROM TagReview tr JOIN tr.tag t WHERE tr.review.reviewId = :reviewId")
    List<Tag> findTagsByReviewId(@Param("reviewId") Long reviewId);
}
