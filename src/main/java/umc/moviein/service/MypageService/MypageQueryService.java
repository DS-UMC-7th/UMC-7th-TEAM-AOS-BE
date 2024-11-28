package umc.moviein.service.MypageService;

import org.springframework.data.domain.Page;
import umc.moviein.web.dto.ReviewResponseDto;
import umc.moviein.web.dto.mypage.MypageResponseDTO;

public interface MypageQueryService {
    MypageResponseDTO.MypageResultDTO getMypage(Integer id);

    // 사용자가 작성한 리뷰 조회 (페이징 지원)
    Page<ReviewResponseDto> getUserReviews(Integer userId, int page, int size);
}
