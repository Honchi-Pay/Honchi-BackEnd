package honchi.api.domain.chat.service;

import honchi.api.domain.chat.domain.Chat;
import honchi.api.domain.chat.domain.enums.Authority;
import honchi.api.domain.chat.domain.repository.ChatRepository;
import honchi.api.domain.chat.dto.ChatListResponse;
import honchi.api.domain.chat.dto.UpdateTitleRequest;
import honchi.api.domain.chat.exception.ChatNotFoundException;
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
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<ChatListResponse> getChat() {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<ChatListResponse> chatListResponses = new ArrayList<>();

        for (Chat chat : chatRepository.findAllByUserId(user.getId())) {
            String title = chat.getTitle();

            if(title.equals("default")) {
                title = chatRepository.findByRoomIdAndAuthority(chat.getRoomId(), Authority.LEADER).getTitle();
                chatRepository.save(chat.updateTitle(title));
            }

            chatListResponses.add(
                    ChatListResponse.builder()
                            .roomId(chat.getRoomId())
                            .title(title)
                            .people(chatRepository.countByRoomId(chat.getRoomId()))
                            .build()
            );
        }

        return chatListResponses;
    }

    @Override
    public void updateTitle(UpdateTitleRequest updateTitleRequest) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        for (Chat chat : chatRepository.findAllByRoomId(updateTitleRequest.getRoomId())) {
            chat.updateTitle(updateTitleRequest.getTitle());
            chatRepository.save(chat);
        }
    }

    @Override
    public void exitChat(String roomId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        chatRepository.findByRoomId(roomId)
                .orElseThrow(ChatNotFoundException::new);

        chatRepository.deleteByUserIdAndRoomId(user.getId(), roomId);
    }
}
