package honchi.api.domain.message.domain.repository;

import honchi.api.domain.message.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAllByChatIdOrderByTimeDesc(String chatId);
    Message findTop1ByChatIdOrderByTimeDesc(String chatId);

    @Query(value = "select * from message where chat_id = :chatId and id > :a and id <= :b", nativeQuery = true)
    List<Message> findByChatIdAndIdAndId(@Param("chatId") String chatId, @Param("a") Integer a, @Param("b") Integer b);
}
