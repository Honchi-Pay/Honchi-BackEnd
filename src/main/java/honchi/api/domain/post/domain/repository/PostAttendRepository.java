package honchi.api.domain.post.domain.repository;

import honchi.api.domain.post.domain.PostAttend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostAttendRepository extends CrudRepository<PostAttend, Integer> {

    Optional<PostAttend> findByPostIdAndUserId(Integer postId, Integer userId);

    void deleteByPostIdAndUserId(Integer postId, Integer userId);
}
