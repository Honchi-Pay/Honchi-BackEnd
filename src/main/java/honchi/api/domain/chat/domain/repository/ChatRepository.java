package honchi.api.domain.chat.domain.repository;

import honchi.api.domain.chat.domain.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Integer> {

    List<Chat> findAllByUserId(Integer userId);
}
