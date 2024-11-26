package umc.moviein.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.web.dto.MovieInfoResponseDto;
import umc.moviein.web.dto.ReviewRequestDto;
import umc.moviein.domain.Review;
import umc.moviein.service.ReviewService;

@RestController("/api")
@RequiredArgsConstructor
public class ReviewController {
    final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody ReviewRequestDto reviewDto) {
        Review review =  reviewService.registerReview(reviewDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(review));

    }
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<MovieInfoResponseDto>> findReviews(@PathVariable(name = "movieId") Long movieId) {
        MovieInfoResponseDto reviews = reviewService.findReviewsByMovieId(movieId);
        return ResponseEntity.ok(ApiResponse.onSuccess(reviews));
    }
}
