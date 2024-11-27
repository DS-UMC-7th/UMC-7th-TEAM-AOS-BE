package umc.moviein.domain;


import jakarta.persistence.*;
import lombok.*;
import umc.moviein.domain.mapping.TagReview;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    List<TagReview> tagReviewList = new ArrayList<>();

}
