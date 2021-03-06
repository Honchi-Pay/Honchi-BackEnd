package honchi.api.domain.user.domain.repository;

import honchi.api.domain.user.domain.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Integer> {
    Optional<Star> findByUserIdAndTargetId(Integer userId, Integer targetId);
    Integer countByTargetId(Integer targetId);
    Optional<Star> findByTargetId(Integer targetId);

    @Query("select sum(star) from Star where targetId = :targetId")
    Double sumStar(@Param("targetId") Integer targetId);
}
