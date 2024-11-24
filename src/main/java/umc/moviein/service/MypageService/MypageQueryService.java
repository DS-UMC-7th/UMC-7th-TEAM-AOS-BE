package umc.moviein.service.MypageService;

import umc.moviein.web.dto.mypage.MypageResponseDTO;

public interface MypageQueryService {
    MypageResponseDTO.MypageResultDTO getMypage(Integer id);
}
