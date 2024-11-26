package umc.moviein.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.converter.UserConverter;
import umc.moviein.domain.Users;
import umc.moviein.service.SignService.SignCommandService;
import umc.moviein.service.SignService.SignQueryService;
import umc.moviein.web.dto.sign.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입/로그인 API", description = "User의 회원가입화면/로그인화면의 API입니다.")
@RequestMapping("/api/sign")
public class SignRestController {

    private final SignCommandService signCommandService;
    private final SignQueryService signQueryService;


    @Operation(summary = "회원가입",description = "아이디/비밀번호/이메일/닉네임을 바탕으로 유저를 생성합니다.")
    @PostMapping("/sign-up")
    public ApiResponse<SignUpResponseDTO.JoinResultDTO> signUp(@RequestBody @Valid SignUpRequestDTO.JoinDto request){
        Users user = signCommandService.signUp(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }
    @Operation(summary = "로그인",description = "loginId와 password를 가지고 로그인합니다.")
    @PostMapping("/sign-in")
    public ApiResponse<SignInResponseDTO.LoginResultDTO> signIn(@RequestBody SignInRequestDTO signInRequestDto){
        SignInResponseDTO.LoginResultDTO responseDto = signCommandService.signIn(signInRequestDto);
        return ApiResponse.onSuccess(responseDto);
    }

    @Operation(summary = "loginId 중복 검사",description = "입력한 Id와 기존 Id를 비교해 중복을 검사합니다.")
    @GetMapping("/id-check")
    public ApiResponse<String> checkIdDuplicate(@RequestParam("loginId") String loginId) {
        return signQueryService.checkIdDuplicate(loginId);
    }

}
