package umc.moviein.web.dto.Movie;

import lombok.*;

import java.util.List;
//외부 API(영화 상세 정보)로 부터 정보 추출
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailDTO {
    private String movieNm;
    private String movieCd;
    private String openDt;
    private String runtime;
    private String watchGradeNm;
    private String director;
    private List<String> actors;
    private String poster;
    private String plot;

}