package umc.moviein.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String movieCd; // 영화 코드

    private String movieNm; //영화 이름
    private String openDt; //개봉일
    private String runtime; //러닝 타임
    private String watchGradeNm; //관람가

    @Column(columnDefinition = "TEXT")
    private String plot; //줄거리
    private String director; //감독

    @ElementCollection
    private List<String> actors; //출연 배우

    @Column(length = 500)
    private String posterUrl; //포스터 이미지 URL

    // Preference
    @OneToMany(mappedBy = "movie")
    private List<Preference> preferences;
}

