package honchi.api.domain.post.service;

import honchi.api.domain.post.domain.Post;
import honchi.api.domain.post.domain.PostAttend;
import honchi.api.domain.post.domain.PostImage;
import honchi.api.domain.post.domain.enums.Category;
import honchi.api.domain.post.domain.enums.Completion;
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
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserImageRepository userImageRepository;
    private final PostImageRepository postImageRepository;
    private final PostAttendRepository postAttendRepository;

    private final AuthenticationFacade authenticationFacade;

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @SneakyThrows
    @Override
    public void write(PostWriteRequest postWriteRequest) {
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

                file.transferTo(new File(imageDirPath, imageName));
                postImages.add(
                        PostImage.builder()
                                .postId(post.getId())
                                .imageName(imageName)
                                .build()
                );
            }
            postRepository.save(post.setImage(postImages));
        }
    }

    @Override
    public List<PostListResponse> getList(PostListRequest postListRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        if (postListRequest.getLat() != 0.0 && postListRequest.getLon() != 0.0)
            userRepository.save(user.updateDist(postListRequest.getLat(), postListRequest.getLon()));

        List<PostListResponse> postListResponses = new ArrayList<>();

        for (Post posts : postRepository.findAllByCompletionAndCreatedAtAfter(
                Completion.UNCOMPLETION, LocalDateTime.now().minusMonths(1))) {
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
                                .createdAt(post.getCreatedAt())
                                .build()
                );
            });

        }

        return postListResponses;
    }

    @Override
    public List<PostListResponse> getSearch(PostSearchListRequest postSearchListRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        if (postSearchListRequest.getLat() != 0.0 && postSearchListRequest.getLon() != 0.0)
            userRepository.save(user.updateDist(postSearchListRequest.getLat(), postSearchListRequest.getLon()));

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

        List<PostAttendListResponse> postAttendListResponses = new ArrayList<>();

        User writer = userRepository.findById(post.getUserId())
                .orElseThrow(UserNotFoundException::new);

        UserImage userImage = userImageRepository.findByUserId(writer.getId());

        postAttendListResponses.add(
                PostAttendListResponse.builder()
                        .userName(writer.getNickName())
                        .image(userImage.getImageName())
                        .build()
        );

        for (PostAttend attend : postAttendRepository.findByPostId(postId)) {
            User attender = userRepository.findById(attend.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            UserImage profileImage = userImageRepository.findByUserId(attender.getId());

            postAttendListResponses.add(
                    PostAttendListResponse.builder()
                            .userName(attender.getNickName())
                            .image(profileImage.getImageName())
                            .build()
            );
        }

        return postAttendListResponses;
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
            Files.delete(new File(imageDirPath, postImage.getImageName()).toPath());
        }

        postImageRepository.deleteByPostId(postId);

        List<PostImage> postImages = new ArrayList<>();

        for (MultipartFile file : postFixRequest.getImages()) {
            String imageName = UUID.randomUUID().toString();

            file.transferTo(new File(imageDirPath, imageName));

            postImages.add(
                    PostImage.builder()
                            .postId(postId)
                            .imageName(imageName)
                            .build()
            );
        }
        postRepository.save(post.setImage(postImages));
    }

    @SneakyThrows
    @Override
    public void deletePost(Integer postId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        for (PostImage postImage : postImageRepository.findAllByPostId(postId)) {
            Files.delete(new File(imageDirPath, postImage.getImageName()).toPath());
        }

        postRepository.deleteById(postId);
    }
}