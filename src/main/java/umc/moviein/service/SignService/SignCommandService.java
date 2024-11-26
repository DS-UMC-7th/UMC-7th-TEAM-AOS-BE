package umc.moviein.service.SignService;

import umc.moviein.domain.Users;
import umc.moviein.web.dto.sign.SignInRequestDTO;
import umc.moviein.web.dto.sign.SignInResponseDTO;
import umc.moviein.web.dto.sign.SignUpRequestDTO;

public interface SignCommandService {

    Users signUp(SignUpRequestDTO.JoinDto signUpRequestDto);
    SignInResponseDTO.LoginResultDTO signIn(SignInRequestDTO requestDto);

    }
