package umc.moviein.service.SignService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.moviein.apiPayload.ApiResponse;
import umc.moviein.apiPayload.Exception.handler.UserHandler;
import umc.moviein.apiPayload.code.status.ErrorStatus;
import umc.moviein.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SignQueryServiceImpl implements SignQueryService{

    private final UserRepository userRepository;
    @Override
    @Transactional
    public ApiResponse<String> checkIdDuplicate(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new UserHandler(ErrorStatus.LOGIN_ID_ALREADY_EXISTS);
        }
        return ApiResponse.onSuccess(loginId);
    }
}
