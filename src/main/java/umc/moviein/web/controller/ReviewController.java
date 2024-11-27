package umc.moviein.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.web.dto.MovieInfoResponseDto;
import umc.moviein.web.dto.ReviewRequestDto;
import umc.moviein.domain.Review;
import umc.moviein.service.ReviewService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDto reviewDto) {
        try {
            Review review = reviewService.registerReview(reviewDto);
            return ResponseEntity.ok("Review created successfully");
        } catch (Exception e) {
            // 예외 처리 (선택적으로 로깅 추가 가능)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create review: " + e.getMessage());
        }

    }
    @GetMapping("/reviews/{movieId}")
    public ResponseEntity<ApiResponse<MovieInfoResponseDto>> findReviews(@PathVariable(name = "movieId") Long movieId) {
        MovieInfoResponseDto reviews = reviewService.findReviewsByMovieId(movieId);
        return ok(ApiResponse.onSuccess(reviews));
    }
}
