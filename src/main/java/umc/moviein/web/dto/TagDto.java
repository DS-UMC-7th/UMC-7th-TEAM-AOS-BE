package umc.moviein.web.dto;


import lombok.*;
import umc.moviein.domain.Tag;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private Long tagId;
    private String tagName;

    public static TagDto convertTagReveiwToDto(Tag tag) {
        return TagDto.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getName())
                .build();
    }
    public static List<TagDto> convertTagDtoTOList(List<Tag> tags) {
        List<TagDto> dtoList = new ArrayList<>();
        for(Tag tag : tags) {
            dtoList.add(TagDto.convertTagReveiwToDto(tag));
        }
        return dtoList;

    }
}