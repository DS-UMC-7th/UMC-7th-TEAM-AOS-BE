package umc.moviein.service.MypageService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.moviein.apiPayload.Exception.handler.UserHandler;
import umc.moviein.apiPayload.code.status.ErrorStatus;
import umc.moviein.domain.Review;
import umc.moviein.domain.User;
import umc.moviein.repository.ReviewRepository;
import umc.moviein.repository.UserRepository;
import umc.moviein.web.dto.ReviewResponseDto;
import umc.moviein.web.dto.TagDto;
import umc.moviein.web.dto.mypage.MypageResponseDTO;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageQueryServiceImpl implements MypageQueryService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public MypageResponseDTO.MypageResultDTO getMypage(Integer id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        return MypageResponseDTO.MypageResultDTO.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getUserReviews(Integer userId, int page, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        Page<Review> reviews = reviewRepository.findByUserId(userId, PageRequest.of(page, size));

        return reviews.map(review -> ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .content(review.getContent())
                .isSpoiled(review.isSpoiled())
                .tagReviewList(review.getTagReviewList().stream()
                        .map(tagReview -> TagDto.builder()
                                .tagId(tagReview.getTag().getTagId())
                                .tagName(tagReview.getTag().getName())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build());
    }
}
