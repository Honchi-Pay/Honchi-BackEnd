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

        chatRepository.findByChatId(imageRequest.getRoomId())
                .orElseThrow(ChatNotFoundException::new);

        String imageName = UUID.randomUUID().toString();

        messageRepository.save(
                Message.builder()
                        .chatId(imageRequest.getRoomId())
                        .userId(user.getId())
                        .message(imageName)
                        .messageType(MessageType.IMAGE)
                        .readCount(chatRepository.countByChatId(imageRequest.getRoomId()) - 1)
                        .isDelete(false)
                        .time(LocalDateTime.now())
                        .build()
        );

        imageRequest.getImage().transferTo(new File(imageDirPath, imageName));
    }

    @Override
    public List<MessageResponse> getList(String chatId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<MessageResponse> messages = new ArrayList<>();

        for (Message message : messageRepository.findAllByChatIdOrderByTimeDesc(chatId)) {
            User user = userRepository.findById(message.getId())
                    .orElseThrow(UserNotFoundException::new);

            messages.add(
                    MessageResponse.builder()
                            .messageId(message.getId())
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
    public void readMessage(String chatId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Chat chat = chatRepository.findByChatIdAndUserId(chatId, user.getId())
                .orElseThrow(ChatNotFoundException::new);

        Message recentMessage = messageRepository.findTop1ByChatIdOrderByTimeDesc(chatId);

        if(!chat.getReadPoint().equals(recentMessage.getId())) {
            for (Message message : messageRepository.findByChatIdAndIdAndId(
                    chatId, chat.getReadPoint(), recentMessage.getId())) {
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

        messageRepository.save(message.delete());
    }
}
