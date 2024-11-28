package umc.moviein.converter;

import umc.moviein.domain.Review;
import umc.moviein.web.dto.ReviewResponseDto;
import umc.moviein.web.dto.TagDto;

import java.util.List;

public class ReviewConverter {
    public static ReviewResponseDto toResponseDto(Review review) {
        List<TagDto> tags = review.getTagReviewList().stream()
                .map(tagReview -> TagDto.builder()
                        .tagId(tagReview.getTag().getTagId())
                        .tagName(tagReview.getTag().getName())
                        .build())
                .toList();

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
