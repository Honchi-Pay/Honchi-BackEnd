package honchi.api.domain.message.service;

import honchi.api.domain.message.dto.MessageRequest;
import honchi.api.domain.message.dto.MessageResponse;

import java.util.List;

public interface MessageService {

    List<MessageResponse> getList(MessageRequest messageListRequest);
}
