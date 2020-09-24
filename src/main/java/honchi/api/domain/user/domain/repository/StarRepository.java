package honchi.api.domain.user.domain.repository;

import honchi.api.domain.user.domain.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Integer> {
    Optional<Star> findByStarredUserId (Integer starred_user_id);
    Integer countByStarredUserId (Integer starred_user_id);

    @Query("select sum(star) from Star where starredUserId = :starred_user_id")
    Double sumStar (@Param("starred_user_id") Integer starred_user_id);
}
