package umc.moviein.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.moviein.domain.common.BaseEntity;
import umc.moviein.domain.mapping.TagReview;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reveiw")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false, columnDefinition = "int default 10")
    private Integer rating;
    @Column(nullable = false, length = 50)
    private String content;
    @Column(nullable = false)
    private boolean isSpoiled = false;

    @OneToMany(mappedBy = "review")
    List<TagReview> tagReviewList = new ArrayList<>();

    private Long movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
