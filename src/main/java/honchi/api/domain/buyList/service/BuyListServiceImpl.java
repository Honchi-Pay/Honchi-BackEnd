package honchi.api.domain.buyList.service;

import honchi.api.domain.buyList.domain.BuyList;
import honchi.api.domain.buyList.domain.repository.BuyListRepository;
import honchi.api.domain.buyList.dto.BuyListResponse;
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
public class BuyListServiceImpl implements BuyListService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BuyListRepository buyListRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<BuyListResponse> getBuyList() {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<BuyListResponse> buyListResponses = new ArrayList<>();

        for (BuyList buyList : buyListRepository.findByUserId(user.getId())) {
            Post post = postRepository.findById(buyList.getPostId())
                    .orElseThrow(PostNotFoundException::new);

            buyListResponses.add(
                    BuyListResponse.builder()
                            .id(buyList.getId())
                            .title(post.getTitle())
                            .price(buyList.getPrice())
                            .time(buyList.getTime())
                            .build()
            );
        }

        return buyListResponses;
    }
}
