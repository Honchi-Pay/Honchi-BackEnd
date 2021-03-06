package honchi.api.domain.message.controller;

import honchi.api.domain.message.dto.ImageRequest;
import honchi.api.domain.message.dto.MessageResponse;
import honchi.api.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public Integer sendImage(@ModelAttribute @Valid ImageRequest imageRequest) {
        return messageService.sendImage(imageRequest);
    }

    @GetMapping("/{chatId}")
    public List<MessageResponse> getMessage(@PathVariable @Valid String chatId) {
        return messageService.getList(chatId);
    }

    @PutMapping("/{chatId}")
    public void readMessage(@PathVariable @Valid String chatId) {
        messageService.readMessage(chatId);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable @Valid Integer messageId) {
        messageService.deleteMessage(messageId);
    }
}
