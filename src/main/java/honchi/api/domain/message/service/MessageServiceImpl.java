package honchi.api.domain.message.service;

import honchi.api.domain.chat.domain.Chat;
import honchi.api.domain.chat.domain.repository.ChatRepository;
import honchi.api.domain.chat.exception.ChatNotFoundException;
import honchi.api.domain.message.domain.Message;
import honchi.api.domain.message.domain.enums.MessageType;
import honchi.api.domain.message.domain.repository.MessageRepository;
import honchi.api.domain.message.dto.ImageRequest;
import honchi.api.domain.message.dto.MessageResponse;
import honchi.api.domain.message.exception.MessageNotFoundException;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.UserNotFoundException;
import honchi.api.global.error.exception.UserNotSameException;
import honchi.api.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final S3Service s3Service;

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    private final AuthenticationFacade authenticationFacade;

    @SneakyThrows
    @Override
    public Integer sendImage(ImageRequest imageRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        chatRepository.findByChatId(imageRequest.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        String imageName = UUID.randomUUID().toString();

        Message message = messageRepository.save(
                Message.builder()
                        .chatId(imageRequest.getChatId())
                        .userId(user.getId())
                        .message(imageName)
                        .messageType(MessageType.IMAGE)
                        .readCount(chatRepository.countByChatId(imageRequest.getChatId()))
                        .isDelete(false)
                        .time(LocalDateTime.now())
                        .build()
        );

        s3Service.upload(imageRequest.getImage(), imageName);

        return message.getId();
    }

    @Override
    public List<MessageResponse> getList(String chatId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<MessageResponse> messages = new ArrayList<>();

        for (Message message : messageRepository.findAllByChatIdOrderByTimeDesc(chatId)) {
            User user = userRepository.findById(message.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            messages.add(
                    MessageResponse.builder()
                            .id(message.getId())
                            .userId(user.getId())
                            .message(message.getMessage())
                            .messageType(message.getMessageType())
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
    public void readMessage(String chatId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Chat chat = chatRepository.findByChatIdAndUserId(chatId, user.getId())
                .orElseThrow(ChatNotFoundException::new);

        Message recentMessage = messageRepository.findTop1ByChatIdOrderByTimeDesc(chatId);

        if(!chat.getReadPoint().equals(recentMessage.getId())) {
            for (Message message : messageRepository.findByChatIdAndIdAndId(
                    chatId, chat.getReadPoint(), recentMessage.getId())) {
                if(message.getMessageType().equals(MessageType.INFO)) continue;
                messageRepository.save(message.updateReadCount());
            }
            chatRepository.save(chat.updateRead(recentMessage.getId()));
        }
    }

    @Override
    public void deleteMessage(Integer messageId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Message message = messageRepository.findById(messageId)
                .orElseThrow(MessageNotFoundException::new);

        if(!user.getId().equals(message.getUserId())) {
            throw new UserNotSameException();
        }

        if(message.getMessageType().equals(MessageType.IMAGE)) {
            s3Service.delete(message.getMessage());
        }

        messageRepository.save(message.delete());
    }
}
