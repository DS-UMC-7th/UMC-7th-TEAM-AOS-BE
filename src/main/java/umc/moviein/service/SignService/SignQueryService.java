package umc.moviein.service.SignService;

import umc.moviein.apiPayload.ApiResponse;

public interface SignQueryService {
    ApiResponse<String> checkIdDuplicate(String loginId);
}
