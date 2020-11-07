package honchi.api.domain.message.service;

import honchi.api.domain.message.domain.Message;
import honchi.api.domain.message.domain.repository.MessageRepository;
import honchi.api.domain.message.dto.MessageListRequest;
import honchi.api.domain.message.dto.MessageResponse;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<MessageResponse> getList(MessageListRequest messageListRequest) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<MessageResponse> messages = new ArrayList<>();

        for (Message message : messageRepository.findAllByRoomIdOrderByTimeDesc(messageListRequest.getRoomId())) {
            User user = userRepository.findById(message.getId())
                    .orElseThrow(UserNotFoundException::new);

            messages.add(
                    MessageResponse.builder()
                            .userId(user.getId())
                            .message(message.getMessage())
                            .nickName(user.getNickName())
                            .time(message.getTime())
                            .isMine(user.getId().equals(message.getUserId()))
                            .build()
            );
        }
        return messages;
    }
}
