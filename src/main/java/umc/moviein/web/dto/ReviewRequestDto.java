package umc.moviein.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc.moviein.domain.Review;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {

    Long movieId;
    Integer rating;
    List<Integer> tags = new ArrayList<>();
    String content;
    boolean isSpoiled;

    public static Review convertDtoToEntity(ReviewRequestDto dto) {
        return Review.builder()
                .movieId(dto.movieId)
                .rating(dto.rating)
                .content(dto.content)
                .isSpoiled(dto.isSpoiled)
                .build();
    }

}
