package honchi.api.domain.user.domain.repository;

import honchi.api.domain.user.domain.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
    UserImage findByImageName(String imageName);
    UserImage findByUserId(Integer userId);
}
