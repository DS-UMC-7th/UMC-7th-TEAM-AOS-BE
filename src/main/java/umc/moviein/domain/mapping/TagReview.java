package umc.moviein.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import umc.moviein.domain.Review;
import umc.moviein.domain.Tag;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tag-review")
public class TagReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
