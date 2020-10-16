package honchi.api.domain.post.domain.repository;

import honchi.api.domain.post.domain.PostAttend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostAttendRepository extends CrudRepository<PostAttend, Integer> {

    Optional<PostAttend> findByPostIdAndUserId(Integer postId, Integer userId);

    List<PostAttend> findByPostId(Integer postId);

    void deleteByPostIdAndUserId(Integer postId, Integer userId);
}
