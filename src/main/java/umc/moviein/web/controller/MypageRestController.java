package umc.moviein.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.moviein.service.MypageService.MypageQueryService;
import umc.moviein.web.dto.mypage.MypageResponseDTO;

import static umc.moviein.jwt.SecurityUtil.getCurrentMemberId;

@RequestMapping("/api/mypage")
@RestController
@RequiredArgsConstructor
public class MypageRestController {
    private final MypageQueryService mypageQueryService;

    @GetMapping("")
    public ResponseEntity<?> getMypage(){
        //로그인 한 현재 사용자의 id를 의미
        Integer id = getCurrentMemberId();

        MypageResponseDTO.MypageResultDTO responseDto = mypageQueryService.getMypage(id);
        return ResponseEntity.ok(responseDto);
    }
}
