package umc.moviein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.moviein.domain.Tag;
@Repository
public interface TagRespsitory extends JpaRepository<Tag, Long> {
}
