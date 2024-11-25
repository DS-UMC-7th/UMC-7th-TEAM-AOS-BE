package umc.moviein.service.MovieService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import umc.moviein.converter.MovieConverter;
import umc.moviein.domain.Movie;
import umc.moviein.repository.MovieRepository;
import umc.moviein.web.dto.Movie.MovieDetailDTO;
import umc.moviein.web.dto.Movie.MovieListDTO;
import umc.moviein.web.dto.Movie.MovieSummaryDTO;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieQueryServiceImpl implements MovieQueryService {
    @Value("${kobis.api.key}")
    private String kobisApiKey;

    @Value("${kmdb.api.key}") // KMDB API 키를 @Value로 가져오기
    private String kmdbApiKey;

    @Autowired
    private final RestTemplate restTemplate;

    private final MovieRepository movieRepository;

    public MovieQueryServiceImpl(RestTemplate restTemplate, MovieRepository movieRepository) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
    }

    // 주간 날짜 생성
    public List<String> generateWeeklyDates(String startDate) {
        List<String> dates = new ArrayList<>();
        LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate today = LocalDate.now();

        while (date.isBefore(today)) {
            dates.add(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            date = date.plusWeeks(1); // 7일씩 건너뛰기
        }

        return dates;
    }

    // 박스오피스 목록 가져오기
    public List<MovieListDTO> fetchMovies(String targetDate) {
        String url = String.format(
                "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=%s&targetDt=%s",
                kobisApiKey, targetDate);

        var response = restTemplate.getForObject(url, Map.class);
        var boxOfficeResult = (Map<String, Object>) response.get("boxOfficeResult");
        var weeklyBoxOfficeList = (List<Map<String, Object>>) boxOfficeResult.get("weeklyBoxOfficeList");

        return weeklyBoxOfficeList.stream()
                .map(movie -> {
                    MovieListDTO dto = new MovieListDTO();
                    dto.setMovieCd((String) movie.get("movieCd"));
                    dto.setMovieNm((String) movie.get("movieNm"));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 영화 상세 정보 가져오기
    @Override
    public MovieDetailDTO fetchMovieDetail(String movieCd) {
        String url = String.format(
                "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=%s&movieCd=%s",
                kobisApiKey, movieCd);

        try {
            var response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("movieInfoResult")) {
                throw new RuntimeException("KOBIS API 응답이 비어있습니다.");
            }

            var movieInfo = (Map<String, Object>) ((Map<String, Object>) response.get("movieInfoResult")).get("movieInfo");
            MovieDetailDTO dto = new MovieDetailDTO();
            dto.setMovieCd(movieCd);
            dto.setMovieNm((String) movieInfo.get("movieNm"));
            dto.setOpenDt((String) movieInfo.get("openDt"));
            dto.setRuntime((String) movieInfo.get("showTm"));

            // 관람 등급 처리
            var audits = (List<Map<String, Object>>) movieInfo.get("audits");
            if (audits != null && !audits.isEmpty()) {
                dto.setWatchGradeNm((String) audits.get(0).get("watchGradeNm"));
            }

            // 감독 처리
            var directors = (List<Map<String, Object>>) movieInfo.get("directors");
            if (directors != null && !directors.isEmpty()) {
                dto.setDirector((String) directors.get(0).get("peopleNm"));
            }

            // 배우 처리: peopleNm만 추출
            var actors = (List<Map<String, Object>>) movieInfo.get("actors");
            if (actors != null && !actors.isEmpty()) {
                dto.setActors(actors.stream()
                        .map(actor -> (String) actor.get("peopleNm"))
                        .toList());
            }

            // KMDB API에서 추가 정보 가져오기
            fetchAdditionalMovieDetails(dto);

            return dto;
        } catch (Exception e) {
            System.err.println("KOBIS API 호출 중 에러 발생: " + e.getMessage());
            return null; // 예외 발생 시 null 반환 또는 기본 값 설정 가능
        }
    }

    // KMDB API를 호출하여 줄거리와 포스터를 가져오는 메서드
    public void fetchAdditionalMovieDetails(MovieDetailDTO dto) {
        String url = String.format(
                "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=%s&detail=Y&query=%s",
                kmdbApiKey, dto.getMovieNm()
        );

        try {
            // API 응답을 String으로 받아옴
            String response = restTemplate.getForObject(url, String.class);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);

            // 필요한 데이터 추출
            var data = ((List<Map<String, Object>>) responseMap.get("Data")).get(0);
            var result = ((List<Map<String, Object>>) data.get("Result")).get(0);

            // 줄거리 설정
            var plots = (Map<String, Object>) result.get("plots");
            if (plots != null) {
                var plotList = (List<Map<String, Object>>) plots.get("plot");
                if (plotList != null && !plotList.isEmpty()) {
                    for (Map<String, Object> plot : plotList) {
                        if ("한국어".equals(plot.get("plotLang"))) {
                            dto.setPlot((String) plot.get("plotText")); // 여기에 줄거리 텍스트 설정
                            break;
                        }
                    }
                }
            }

            // 포스터 설정
            String posters = (String) result.get("posters");
            if (posters != null && !posters.isBlank()) {
                dto.setPoster(posters.split("\\|")[0]); // 첫 번째 포스터 URL 사용
            } else {
                dto.setPoster(null);
            }
        } catch (Exception e) {
            System.err.println("KMDB API 호출 중 에러 발생: " + e.getMessage());
            dto.setPlot("줄거리 정보를 가져올 수 없습니다.");
            dto.setPoster(null);
        }
    }

    public Page<MovieSummaryDTO> getMoviesWithPagination(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(MovieConverter::toSummaryDTO);
    }

    public MovieDetailDTO getMovieDetailById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + id));
        return MovieConverter.toDTO(movie);
    }
}