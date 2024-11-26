package umc.moviein.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc.moviein.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private Long reviewId;
    private Integer rating;
    private String content;
    private Boolean isSpoiled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TagDto> tagReviewList; // 태그 정보 포함

//    private String userImage;


    public static ReviewResponseDto convertEntityToDto(Review review, List<TagDto> tags) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .content(review.getContent())
                .isSpoiled(review.isSpoiled())
                .tagReviewList(tags)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }





}
