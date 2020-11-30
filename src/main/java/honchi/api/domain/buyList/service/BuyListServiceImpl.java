package honchi.api.domain.buyList.service;

import honchi.api.domain.buyList.domain.BuyList;
import honchi.api.domain.buyList.domain.repository.BuyListRepository;
import honchi.api.domain.buyList.dto.BuyContentResponse;
import honchi.api.domain.buyList.dto.BuyListResponse;
import honchi.api.domain.buyList.dto.SetPriceRequest;
import honchi.api.domain.buyList.exception.BuyListNotFoundException;
import honchi.api.domain.chat.domain.repository.ChatRepository;
import honchi.api.domain.chat.exception.ChatNotFoundException;
import honchi.api.domain.post.domain.Post;
import honchi.api.domain.post.domain.PostAttend;
import honchi.api.domain.post.domain.repository.PostAttendRepository;
import honchi.api.domain.post.domain.repository.PostImageRepository;
import honchi.api.domain.post.domain.repository.PostRepository;
import honchi.api.domain.post.dto.PostAttendListResponse;
import honchi.api.domain.post.exception.PostNotFoundException;
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
public class BuyListServiceImpl implements BuyListService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRepository chatRepository;
    private final BuyListRepository buyListRepository;
    private final UserImageRepository userImageRepository;
    private final PostImageRepository postImageRepository;
    private final PostAttendRepository postAttendRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void setPrice(SetPriceRequest setPriceRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        postRepository.findById(setPriceRequest.getPostId())
                .orElseThrow(PostNotFoundException::new);

        chatRepository.findByChatIdAndUserId(setPriceRequest.getChatId(), user.getId())
                .orElseThrow(ChatNotFoundException::new);

        buyListRepository.save(
                BuyList.builder()
                        .postId(setPriceRequest.getPostId())
                        .userId(user.getId())
                        .price(setPriceRequest.getPrice())
                        .build()
        );
    }

    @Override
    public List<BuyListResponse> getBuyList() {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<BuyListResponse> buyListResponses = new ArrayList<>();

        for (BuyList buyList : buyListRepository.findByUserIdAndTimeIsNotNull(user.getId())) {
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

    @Override
    public List<BuyContentResponse> getContent(Integer buyId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<BuyContentResponse> buyContentResponses = new ArrayList<>();

        BuyList buyList = buyListRepository.findById(buyId)
                .orElseThrow(BuyListNotFoundException::new);

        Post post = postRepository.findById(buyList.getPostId())
                .orElseThrow(PostNotFoundException::new);

        List<String> postImages = new ArrayList<>();
        postImageRepository.findAllByPostId(post.getId())
                .forEach(postImage -> postImages.add(postImage.getImageName()));

        List<PostAttendListResponse> postAttends = new ArrayList<>();
        for (PostAttend postAttend : postAttendRepository.findByPostId(post.getId())) {
            User user = userRepository.findById(postAttend.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            UserImage userImage = userImageRepository.findByUserId(user.getId());

            postAttends.add(
                    PostAttendListResponse.builder()
                            .nickName(user.getNickName())
                            .image(userImage == null ? null : userImage.getImageName())
                            .build()
            );
        }

        buyContentResponses.add(
                BuyContentResponse.builder()
                        .title(post.getTitle())
                        .price(buyList.getPrice())
                        .time(buyList.getTime())
                        .images(postImages)
                        .attendList(postAttends)
                        .build()
        );

        return buyContentResponses;
    }
}
