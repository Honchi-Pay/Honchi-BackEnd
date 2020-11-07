package honchi.api.domain.message.service;

import honchi.api.domain.message.dto.MessageListRequest;
import honchi.api.domain.message.dto.MessageResponse;

import java.util.List;

public interface MessageService {

    List<MessageResponse> getList(MessageListRequest messageListRequest);
}
