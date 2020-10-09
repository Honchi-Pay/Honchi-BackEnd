package honchi.api.domain.post.domain.repository;

import honchi.api.domain.post.domain.PostImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends CrudRepository<PostImage, Integer> {
}
