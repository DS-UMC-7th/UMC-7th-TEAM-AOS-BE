package umc.moviein.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.converter.PreferenceConverter;
import umc.moviein.domain.Movie;
import umc.moviein.service.MypageService.MypageQueryService;
import umc.moviein.service.Preference.PreferenceService;
import umc.moviein.web.dto.Preference.PreferenceRequestDTO;
import umc.moviein.web.dto.Preference.PreferenceResponseDTO;

import static umc.moviein.jwt.SecurityUtil.getCurrentMemberId;

@RestController
@RequiredArgsConstructor
@Tag(name = "좋아요/싫어요 API", description = "영화의 좋아요/싫어요 API 입니다.")
@RequestMapping("/api/like")
public class PreferenceController {
    private final PreferenceService preferenceService;
    private final MypageQueryService mypageQueryService;

    @PostMapping("/{movieId}")
    public ApiResponse<PreferenceResponseDTO.CreateResponseDTO> createMoviePreference(
            @PathVariable("movieId") Long movieId,
            @Valid @RequestBody PreferenceRequestDTO.CreateRequestDTO createRequestDTO
    ) {
        Integer userId = getCurrentMemberId();
        Movie movie = preferenceService.createMoviePreference(userId, movieId, createRequestDTO);
        return ApiResponse.onSuccess(PreferenceConverter.toCreateResponseDTO(movie));
    }
}
