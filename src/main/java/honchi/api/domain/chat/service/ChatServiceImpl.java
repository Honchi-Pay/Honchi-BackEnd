package honchi.api.domain.chat.service;

import honchi.api.domain.chat.domain.Chat;
import honchi.api.domain.chat.domain.enums.Authority;
import honchi.api.domain.chat.domain.repository.ChatRepository;
import honchi.api.domain.chat.dto.ChatListResponse;
import honchi.api.domain.chat.dto.UpdateTitleRequest;
import honchi.api.domain.chat.exception.ChatNotFoundException;
import honchi.api.domain.message.domain.Message;
import honchi.api.domain.message.domain.repository.MessageRepository;
import honchi.api.domain.post.domain.PostAttend;
import honchi.api.domain.post.domain.repository.PostAttendRepository;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.UserImage;
import honchi.api.domain.user.domain.repository.UserImageRepository;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final PostAttendRepository postAttendRepository;

    private final MessageRepository messageRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<ChatListResponse> getChat() {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<ChatListResponse> chatListResponses = new ArrayList<>();

        for (Chat chat : chatRepository.findAllByUserId(user.getId())) {
            String title = chat.getTitle();

            if(title.equals("default")) {
                title = chatRepository.findByChatIdAndAuthority(chat.getChatId(), Authority.LEADER).getTitle();
                chatRepository.save(chat.updateTitle(title));
            }

            List<String> images = new ArrayList<>();

            for (PostAttend postAttend : postAttendRepository.findByPostId(chat.getPostId())) {
                UserImage userImage = userImageRepository.findByUserId(postAttend.getUserId());

                if(userImage.getUserId().equals(user.getId())) continue;

                images.add(userImage == null ? null : userImage.getImageName());

                if(images.size() == 4) break;
            }

            String[] imageArray = images.stream().toArray(String[]::new);

            Message message = messageRepository.findTop1ByChatIdOrderByTimeDesc(chat.getChatId());

            chatListResponses.add(
                    ChatListResponse.builder()
                            .chatId(chat.getChatId())
                            .title(title)
                            .people(chatRepository.countByChatId(chat.getChatId()))
                            .message(message.getMessage())
                            .images(imageArray)
                            .build()
            );
        }

        return chatListResponses;
    }

    @Override
    public void updateTitle(String chatId, UpdateTitleRequest updateTitleRequest) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        for (Chat chat : chatRepository.findAllByChatId(chatId)) {
            chatRepository.save(chat.updateTitle(updateTitleRequest.getTitle()));
        }
    }

    @Override
    public void exitChat(String chatId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        chatRepository.findByChatId(chatId)
                .orElseThrow(ChatNotFoundException::new);

        chatRepository.deleteByChatIdAndUserId(chatId, user.getId());
    }
}