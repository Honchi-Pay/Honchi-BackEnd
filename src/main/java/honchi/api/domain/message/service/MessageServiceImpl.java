package honchi.api.domain.message.service;

import honchi.api.domain.chat.domain.repository.ChatRepository;
import honchi.api.domain.chat.exception.ChatNotFoundException;
import honchi.api.domain.message.domain.Message;
import honchi.api.domain.message.domain.enums.MessageType;
import honchi.api.domain.message.domain.repository.MessageRepository;
import honchi.api.domain.message.dto.ImageRequest;
import honchi.api.domain.message.dto.MessageResponse;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    private final AuthenticationFacade authenticationFacade;

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @SneakyThrows
    @Override
    public void sendImage(ImageRequest imageRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        chatRepository.findByRoomId(imageRequest.getRoomId())
                .orElseThrow(ChatNotFoundException::new);

        String imageName = UUID.randomUUID().toString();

        messageRepository.save(
                Message.builder()
                        .chatId(imageRequest.getRoomId())
                        .userId(user.getId())
                        .message(imageName)
                        .messageType(MessageType.IMAGE)
                        .readCount(chatRepository.countByRoomId(imageRequest.getRoomId()) - 1)
                        .isDelete(false)
                        .time(LocalDateTime.now())
                        .build()
        );

        imageRequest.getImage().transferTo(new File(imageDirPath, imageName));
    }

    @Override
    public List<MessageResponse> getList(String roomId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<MessageResponse> messages = new ArrayList<>();

        for (Message message : messageRepository.findAllByChatIdOrderByTimeDesc(roomId)) {
            User user = userRepository.findById(message.getId())
                    .orElseThrow(UserNotFoundException::new);

            messages.add(
                    MessageResponse.builder()
                            .userId(user.getId())
                            .message(message.getMessage())
                            .nickName(user.getNickName())
                            .time(message.getTime())
                            .readCount(message.getReadCount())
                            .isDelete(message.isDelete())
                            .isMine(user.getId().equals(message.getUserId()))
                            .build()
            );
        }
        return messages;
    }

    @Override
    public void readMessage() {
        
    }
}
