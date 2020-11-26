package honchi.api.domain.chat.service;

import honchi.api.domain.chat.dto.ChatListResponse;
import honchi.api.domain.chat.dto.UpdateTitleRequest;

import java.util.List;

public interface ChatService {

    List<ChatListResponse> getChat();
    void updateTitle(String chatId, UpdateTitleRequest updateTitleRequest);
    void exitChat(String chatId);
}
