package honchi.api.domain.chat.service;

import honchi.api.domain.chat.domain.Chat;
import honchi.api.domain.chat.domain.repository.ChatRepository;
import honchi.api.domain.chat.dto.ChatListResponse;
import honchi.api.domain.post.domain.Post;
import honchi.api.domain.post.domain.repository.PostRepository;
import honchi.api.domain.post.exception.PostNotFoundException;
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
    private final PostRepository postRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<ChatListResponse> getChat() {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<ChatListResponse> chatListResponses = new ArrayList<>();

        for (Chat chat : chatRepository.findAllByUserId(user.getId())) {
            Post post = postRepository.findById(chat.getPostId())
                    .orElseThrow(PostNotFoundException::new);

            chatListResponses.add(
                    ChatListResponse.builder()
                            .roomId(chat.getRoomId())
                            .title(post.getTitle())
                            .build()
            );
        }

        return chatListResponses;
    }
}
