package umc.moviein.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.moviein.domain.common.BaseEntity;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //로그인 시 사용하는 id
    @Column(nullable = false, length = 100)
    private String loginId;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

}
