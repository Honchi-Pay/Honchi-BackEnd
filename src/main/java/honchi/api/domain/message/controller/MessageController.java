package honchi.api.domain.message.controller;

import honchi.api.domain.message.dto.ImageRequest;
import honchi.api.domain.message.dto.MessageRequest;
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
    public void sendImage(@ModelAttribute @Valid ImageRequest imageRequest) {
        messageService.sendImage(imageRequest);
    }

    @GetMapping
    public List<MessageResponse> getMessage(@RequestBody @Valid MessageRequest messageRequest) {
        return messageService.getList(messageRequest);
    }
    @GetMapping("/read")
    public void readMessage() {
        messageService.readMessage();
    }
}
