package umc.moviein.service.SignService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.moviein.apiPayload.Exception.handler.UserHandler;
import umc.moviein.apiPayload.code.status.ErrorStatus;
import umc.moviein.converter.UserConverter;
import umc.moviein.domain.User;
import umc.moviein.jwt.TokenProvider;
import umc.moviein.repository.UserRepository;
import umc.moviein.web.dto.sign.*;

@Service
@RequiredArgsConstructor
public class SignCommandServiceImpl implements SignCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;


    @Override
    @Transactional
    public User signUp(SignUpRequestDTO.JoinDto signUpRequestDto) {
        // 중복 확인
        if (userRepository.existsByLoginId(signUpRequestDto.getLoginId())) {
            throw new UserHandler(ErrorStatus.USER_ALREADY_EXISTS);  // 수정된 에러 메시지
        }
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        User user = UserConverter.toUser(signUpRequestDto, encodedPassword);

        return userRepository.save(user);
    }
    //사실 get이라서 query서비스로 가야하지만 너무 짧아서 여기에 두겠습니다


    @Override
    @Transactional
    public SignInResponseDTO.LoginResultDTO signIn(SignInRequestDTO requestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getLoginId(), requestDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. 응답 DTO 반환
        return UserConverter.toSignInResponseDto(tokenDto);

    }



}

