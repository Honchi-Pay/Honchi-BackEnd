package honchi.api.domain.chat.controller;

import honchi.api.domain.chat.dto.ChatListResponse;
import honchi.api.domain.chat.dto.UpdateTitleRequest;
import honchi.api.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PutMapping("{/chatId}")
    public void updateTitle(@PathVariable @Valid String chatId,
                            @RequestBody @Valid UpdateTitleRequest updateTitleRequest) {
        chatService.updateTitle(chatId, updateTitleRequest);
    }

    @DeleteMapping("/{chatId}")
    public void exitChat(@PathVariable @Valid String chatId) {
        chatService.exitChat(chatId);
    }
}
