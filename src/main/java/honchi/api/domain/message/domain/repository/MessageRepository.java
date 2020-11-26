package honchi.api.domain.message.domain.repository;

import honchi.api.domain.message.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAllByChatIdOrderByTimeDesc(String chatId);
    Message findTop1ByChatIdOrderByTimeDesc(String chatId);
}
