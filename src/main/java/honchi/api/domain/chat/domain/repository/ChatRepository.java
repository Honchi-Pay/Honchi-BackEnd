package honchi.api.domain.chat.domain.repository;

import honchi.api.domain.chat.domain.Chat;
import honchi.api.domain.chat.domain.enums.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Integer> {

    List<Chat> findAllByUserId(Integer userId);
    List<Chat> findAllByRoomId(String roomId);
    Optional<Chat> findByRoomId(String roomId);
    Chat findByRoomIdAndAuthority(String roomId, Authority authority);
    Integer countByRoomId(String roomId);
    void deleteByRoomIdAndUserId(String roomId, Integer userId);
}
