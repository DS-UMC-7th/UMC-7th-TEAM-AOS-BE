package umc.moviein.service.SignService;

import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.domain.User;
import umc.moviein.web.dto.sign.SignInRequestDTO;
import umc.moviein.web.dto.sign.SignInResponseDTO;
import umc.moviein.web.dto.sign.SignUpRequestDTO;
import umc.moviein.web.dto.sign.LoginIdCheckResponseDTO;

public interface SignCommandService {

    User signUp(SignUpRequestDTO.JoinDto signUpRequestDto);
    SignInResponseDTO.LoginResultDTO signIn(SignInRequestDTO requestDto);

    }
