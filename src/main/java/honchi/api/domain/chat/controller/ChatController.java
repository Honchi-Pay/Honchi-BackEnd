package honchi.api.domain.chat.controller;

import honchi.api.domain.chat.dto.ChatListResponse;
import honchi.api.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public List<ChatListResponse> getChat() {
        return chatService.getChat();
    }
}
