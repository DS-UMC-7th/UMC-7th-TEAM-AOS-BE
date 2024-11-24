package umc.moviein.service.MypageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.ErrorHandler;
import umc.moviein.apiPayload.Exception.handler.UserHandler;
import umc.moviein.apiPayload.code.status.ErrorStatus;
import umc.moviein.domain.User;
import umc.moviein.repository.UserRepository;
import umc.moviein.web.dto.mypage.MypageResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageQueryServiceImpl implements MypageQueryService {

    private final UserRepository userRepository;

    @Transactional
    public MypageResponseDTO.MypageResultDTO getMypage(Integer id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        return MypageResponseDTO.MypageResultDTO.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

}
