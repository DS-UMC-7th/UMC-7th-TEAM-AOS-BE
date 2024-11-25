package umc.moviein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.moviein.domain.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

}
