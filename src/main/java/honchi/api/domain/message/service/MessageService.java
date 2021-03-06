package honchi.api.domain.message.service;

import honchi.api.domain.message.dto.ImageRequest;
import honchi.api.domain.message.dto.MessageResponse;

import java.util.List;

public interface MessageService {

    Integer sendImage(ImageRequest imageRequest);
    List<MessageResponse> getList(String chatId);
    void readMessage(String chatId);
    void deleteMessage(Integer messageId);
}
