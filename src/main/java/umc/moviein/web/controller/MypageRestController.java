package umc.moviein.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.service.MypageService.MypageQueryService;
import umc.moviein.web.dto.ReviewResponseDto;
import umc.moviein.web.dto.mypage.MypageResponseDTO;

import static umc.moviein.jwt.SecurityUtil.getCurrentMemberId;

@RequestMapping("/api/mypage")
@RestController
@RequiredArgsConstructor
public class MypageRestController {
    private final MypageQueryService mypageQueryService;

    @GetMapping("")
    public ResponseEntity<?> getMypage(){
        //로그인 한 현재 사용자의 id를 의미
        Integer id = getCurrentMemberId();

        MypageResponseDTO.MypageResultDTO responseDto = mypageQueryService.getMypage(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{userId}/reviews")
    @Operation(summary = "유저가 작성한 리뷰 조회", description = "유저가 작성한 리뷰를 조회합니다. page는 가져올 페이지, size는 한번에 가져올 양입니다.")
    public ApiResponse<Page<ReviewResponseDto>> getUserReviews(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        Page<ReviewResponseDto> reviews = mypageQueryService.getUserReviews(userId, page, size);
        return ApiResponse.onSuccess(reviews);
    }
}
