package umc.moviein.web.dto.Movie;

import lombok.*;
//외부 API(박스 오피스 리스트)로 부터 정보 추출
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieListDTO {
    private String movieNm;
    private String movieCd;
    private String openDt;
}
