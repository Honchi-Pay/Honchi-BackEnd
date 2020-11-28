package honchi.api.domain.chat.domain.repository;

import honchi.api.domain.chat.domain.Chat;
import honchi.api.domain.chat.domain.enums.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, String> {

    List<Chat> findAllByUserId(Integer userId);
    List<Chat> findAllByChatId(String chatId);
    Optional<Chat> findByChatId(String chatId);
    Optional<Chat> findByChatIdAndUserId(String chatId, Integer userId);
    Chat findByChatIdAndAuthority(String chatId, Authority authority);
    Integer countByChatId(String chatId);
    void deleteByChatIdAndUserId(String chatId, Integer userId);
}
