package honchi.api.domain.post.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import honchi.api.domain.buyList.domain.BuyList;
import honchi.api.domain.buyList.domain.repository.BuyListRepository;
import honchi.api.domain.post.domain.Post;
import honchi.api.domain.post.domain.PostAttend;
import honchi.api.domain.post.domain.PostImage;
import honchi.api.domain.post.domain.enums.Category;
import honchi.api.domain.post.domain.enums.Completion;
import honchi.api.domain.post.domain.kakaoMap.KakaoResponseVO;
import honchi.api.domain.post.domain.repository.PostAttendRepository;
import honchi.api.domain.post.domain.repository.PostImageRepository;
import honchi.api.domain.post.domain.repository.PostRepository;
import honchi.api.domain.post.dto.*;
import honchi.api.domain.post.exception.PostNotFoundException;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.UserImage;
import honchi.api.domain.user.domain.repository.UserImageRepository;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.UserNotFoundException;
import honchi.api.global.error.exception.UserNotSameException;
import honchi.api.global.s3.S3Service;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final S3Service s3Service;

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BuyListRepository buyListRepository;
    private final UserImageRepository userImageRepository;
    private final PostImageRepository postImageRepository;
    private final PostAttendRepository postAttendRepository;

    private final AuthenticationFacade authenticationFacade;

    @Value("${map.api.key}")
    private String mapApiKey;

    @SneakyThrows
    @Override
    public Integer write(PostWriteRequest postWriteRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Category item = postWriteRequest.getCategory().equals(Category.FOOD) ?
                Category.FOOD : Category.PRODUCT;

        Post post = postRepository.save(
                Post.builder()
                        .title(postWriteRequest.getTitle())
                        .content(postWriteRequest.getContent())
                        .userId(user.getId())
                        .category(postWriteRequest.getCategory())
                        .item(item.getCategory(postWriteRequest.getItem()))
                        .lat(postWriteRequest.getLat())
                        .lon(postWriteRequest.getLon())
                        .createdAt(LocalDateTime.now())
                        .completion(Completion.UNCOMPLETION)
                        .build()
        );

        List<PostImage> postImages = new ArrayList<>();

        if (postWriteRequest.getImages() != null) {
            for (MultipartFile file : postWriteRequest.getImages()) {
                String imageName = UUID.randomUUID().toString();

                s3Service.upload(file, imageName);
                postImages.add(
                        PostImage.builder()
                                .postId(post.getId())
                                .imageName(imageName)
                                .build()
                );
            }
            postRepository.save(post.setImage(postImages));
        }

        return post.getId();
    }

    @Override
    public List<RecentPostListResponse> getRecentList(Category category) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<RecentPostListResponse> postRecentResponses = new ArrayList<>();

        for (Post post : postRepository.findAllByCompletionAndCategoryAndCreatedAtAfter(
                Completion.UNCOMPLETION, category, LocalDateTime.now().minusHours(2))) {
            User writer = userRepository.findById(post.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            PostImage postImage = postImageRepository.findTop1ByPostId(post.getId());

            postRecentResponses.add(
                    RecentPostListResponse.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .writer(writer.getNickName())
                            .item(post.getItem())
                            .lat(post.getLat())
                            .lon(post.getLon())
                            .createdAt(post.getCreatedAt())
                            .image(postImage == null ? null : postImage.getImageName())
                            .build()
            );
        }

        return postRecentResponses;
    }

    @Override
    public List<PostListResponse> getList(PostListRequest postListRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<PostListResponse> postListResponses = new ArrayList<>();

        for (Post posts : postRepository.findAllByCompletionAndCreatedAtAfterAndCategoryAndItem(
                Completion.UNCOMPLETION, LocalDateTime.now().minusMonths(1),
                postListRequest.getCategory(), postListRequest.getItem())) {

            Optional<Post> list = postRepository.findByIdAndLatAndLon(posts.getId(), posts.getLat(),
                    posts.getLon(), user.getLat(), user.getLon(), postListRequest.getDist());

            list.ifPresent(post -> {
                User writer = userRepository.findById(post.getUserId())
                        .orElseThrow(UserNotFoundException::new);

                PostImage postImage = postImageRepository.findTop1ByPostId(post.getId());

                postListResponses.add(
                        PostListResponse.builder()
                                .postId(post.getId())
                                .title(post.getTitle())
                                .writer(writer.getNickName())
                                .image(postImage != null ? postImage.getImageName() : null)
                                .address(getAddress(post.getLat(), post.getLon()))
                                .createdAt(post.getCreatedAt())
                                .build()
                );
            });

        }

        return postListResponses;
    }

    @Override
    public List<PostListResponse> searchPost(PostSearchListRequest postSearchListRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<PostListResponse> postListResponses = new ArrayList<>();

        for (Post post : postRepository.findAllByTitleContainsAndCompletionAndCreatedAtAfter(
                postSearchListRequest.getTitle(), Completion.UNCOMPLETION, LocalDateTime.now().minusMonths(1))) {
            Optional<Post> postList = postRepository.findByIdAndLatAndLon(post.getId(), post.getLat(),
                    post.getLon(), user.getLat(), user.getLon(), postSearchListRequest.getDist());

            postList.ifPresent(list -> {
                User writer = userRepository.findById(post.getUserId())
                        .orElseThrow(UserNotFoundException::new);

                PostImage postImage = postImageRepository.findTop1ByPostId(post.getId());

                postListResponses.add(
                        PostListResponse.builder()
                                .postId(list.getId())
                                .title(list.getTitle())
                                .writer(writer.getNickName())
                                .image(postImage != null ? postImage.getImageName() : null)
                                .address(getAddress(list.getLat(), list.getLon()))
                                .createdAt(list.getCreatedAt())
                                .build()
                );
            });
        }

        return postListResponses;
    }

    @Override
    public PostContentResponse getContent(Integer postId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User writer = userRepository.findById(post.getUserId())
                .orElseThrow(UserNotFoundException::new);

        List<String> postImages = new ArrayList<>();

        postImageRepository.findAllByPostId(postId)
                .forEach(postImage -> postImages.add(postImage.getImageName()));

        return PostContentResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(writer.getNickName())
                .address(getAddress(post.getLat(), post.getLon()))
                .images(postImages)
                .createdAt(post.getCreatedAt())
                .isMine(writer.equals(user))
                .isAttend(postAttendRepository.findByPostIdAndUserId(postId, user.getId()).isPresent())
                .build();
    }

    @Override
    public List<PostAttendListResponse> getAttendList(Integer postId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User writer = userRepository.findById(post.getUserId())
                .orElseThrow(UserNotFoundException::new);

        List<PostAttendListResponse> postAttendListResponses = new ArrayList<>();

        UserImage userImage = userImageRepository.findByUserId(writer.getId());

        postAttendListResponses.add(
                PostAttendListResponse.builder()
                        .nickName(writer.getNickName())
                        .image(userImage.getImageName())
                        .build()
        );

        for (PostAttend attend : postAttendRepository.findByPostId(postId)) {
            User attender = userRepository.findById(attend.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            UserImage profileImage = userImageRepository.findByUserId(attender.getId());

            postAttendListResponses.add(
                    PostAttendListResponse.builder()
                            .nickName(attender.getNickName())
                            .image(profileImage == null ? null : profileImage.getImageName())
                            .build()
            );
        }

        return postAttendListResponses;
    }

    @Override
    public void updatePoint(UpdatePointRequest updatePointRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        userRepository.save(user.updatePoint(updatePointRequest));
    }

    @Override
    public void attendPost(Integer postId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        if (postAttendRepository.findByPostIdAndUserId(postId, user.getId()).isPresent()) {
            postAttendRepository.deleteByPostIdAndUserId(postId, user.getId());
        } else {
            postAttendRepository.save(
                    PostAttend.builder()
                            .postId(postId)
                            .userId(user.getId())
                            .build()
            );
        }
    }

    @SneakyThrows
    @Override
    @Transactional
    public void fixPost(Integer postId, PostFixRequest postFixRequest) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postRepository.save(post.updateContent(postFixRequest));

        for (PostImage postImage : postImageRepository.findByPostIdOrderById(postId)) {
            s3Service.delete(postImage.getImageName());
        }

        postImageRepository.deleteByPostId(postId);

        List<PostImage> postImages = new ArrayList<>();

        for (MultipartFile file : postFixRequest.getImages()) {
            String imageName = UUID.randomUUID().toString();

            s3Service.upload(file, imageName);

            postImages.add(
                    PostImage.builder()
                            .postId(postId)
                            .imageName(imageName)
                            .build()
            );
        }
        postRepository.save(post.setImage(postImages));
    }

    @Override
    public void complete(Integer postId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if(!user.getId().equals(post.getUserId())) {
            throw new UserNotSameException();
        }

        postRepository.save(post.complete());

        for (BuyList buyList : buyListRepository.findByPostId(postId)) {
            buyListRepository.save(buyList.updateTime());
        }
    }

    @SneakyThrows
    @Override
    public void deletePost(Integer postId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        for (PostImage postImage : postImageRepository.findAllByPostId(postId)) {
            s3Service.delete(postImage.getImageName());
        }

        postRepository.deleteById(postId);
    }

    @SneakyThrows
    private String getAddress (double lat, double lon) {
        try {
            String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2address.json?input_coord=WGS84&x=" + lon + "&y=" + lat;

            HttpResponse<JsonNode> response = Unirest.get(apiUrl)
                    .header("Authorization", "KakaoAK " + mapApiKey)
                    .asJson();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            KakaoResponseVO kakaoResponseVO = objectMapper.readValue(response.getBody().toString(), KakaoResponseVO.class);

            return kakaoResponseVO.getDocuments().get(0).getRoad_address().get("building_name") != "" ?
                    (String) kakaoResponseVO.getDocuments().get(0).getRoad_address().get("building_name") :
                    (String) kakaoResponseVO.getDocuments().get(0).getRoad_address().get("address_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}