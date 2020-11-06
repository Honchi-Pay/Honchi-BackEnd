package honchi.api.domain.message.controller;

import honchi.api.domain.message.dto.MessageListRequest;
import honchi.api.domain.message.dto.MessageResponse;
import honchi.api.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<MessageResponse> getMessage(@RequestBody @Valid MessageListRequest messageListRequest) {
        return messageService.getList(messageListRequest);
    }
}
