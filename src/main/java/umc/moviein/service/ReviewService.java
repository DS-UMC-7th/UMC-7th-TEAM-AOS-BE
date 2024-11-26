package umc.moviein.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.moviein.repository.UserRepository;
import umc.moviein.web.dto.MovieInfoResponseDto;
import umc.moviein.web.dto.ReviewRequestDto;
import umc.moviein.web.dto.ReviewResponseDto;
import umc.moviein.web.dto.TagDto;
import umc.moviein.domain.Review;
import umc.moviein.domain.Tag;
import umc.moviein.domain.mapping.TagReview;
import umc.moviein.repository.ReviewRepository;
import umc.moviein.repository.TagRespsitory;
import umc.moviein.repository.TagReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static umc.moviein.jwt.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Builder
public class ReviewService {
    final ReviewRepository reviewRepository;
    final TagRespsitory tagRespsitory;
    final TagReviewRepository tagReviewRepository;
    final UserRepository userRepository;

    public Review registerReview(ReviewRequestDto dto) {
        Review review = ReviewRequestDto.convertDtoToEntity(dto);
        // Todo 예외처리
        review.setUser(userRepository.findById(getCurrentMemberId()).orElseThrow(() -> new IllegalArgumentException("not valid user in review")));
        tagReviewRepository.saveAll(createOrUpdateTags(dto.getTags(), review));

        reviewRepository.save(review);
        return review;
    }

    public List<TagReview> createOrUpdateTags(List<Integer> tags, Review review) {
        List<TagReview> tagReviews = new ArrayList<>();
        for (Integer tagId : tags) {
            // 태그 조회
            Tag tag = tagRespsitory.findById(tagId.longValue())
                    .orElseGet(() -> {
                        // 태그가 없으면 새로 생성
                        Tag newTag = new Tag();
                        newTag.setTagId(tagId.longValue()); // ID 설정 (필요한 경우)
                        newTag.setName("New Tag " + tagId); // 태그 이름 설정 (적절히 변경)
                        return tagRespsitory.save(newTag);
                    });

            // 태그 리뷰 생성
            TagReview tagReview = TagReview.builder()
                    .review(review)
                    .tag(tag)
                    .build();
            tagReviews.add(tagReview);
        }
        return tagReviews;

    }
    public MovieInfoResponseDto findReviewsByMovieId(Long movieId) {
        // 영화 id로 리뷰 필터링
        List<Review> reviewList = reviewRepository.findAllByMovieId(movieId)
                .orElseThrow(() -> new IllegalArgumentException("No reviews found for movieId: " + movieId));

        List<ReviewResponseDto> reviews = reviewList.stream()
                .map(review -> {
                    // 태그 정보
                    List<TagDto> tagDtos = TagDto.convertTagDtoTOList(
                            tagReviewRepository.findTagsByReviewId(review.getReviewId()));

                    return ReviewResponseDto.convertEntityToDto(review, tagDtos);
                })
                .collect(Collectors.toList());

        return MovieInfoResponseDto.builder()
                .movieId(movieId)
                .reviews(reviews)
                .build();
    }
}

