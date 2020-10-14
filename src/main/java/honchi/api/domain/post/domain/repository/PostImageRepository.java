package honchi.api.domain.post.domain.repository;

import honchi.api.domain.post.domain.PostImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends CrudRepository<PostImage, Integer> {

    List<PostImage> findAllByPostId(Integer postId);

    PostImage findTop1ByPostId(Integer postId);
}
