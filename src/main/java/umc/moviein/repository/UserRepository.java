package umc.moviein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.moviein.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

}
