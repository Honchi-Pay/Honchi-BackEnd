package honchi.api.domain.chat.service;

import honchi.api.domain.chat.dto.ChatListResponse;

import java.util.List;

public interface ChatService {

    List<ChatListResponse> getChat();
}
