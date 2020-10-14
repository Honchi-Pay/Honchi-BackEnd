package honchi.api.domain.post.service;

import honchi.api.domain.post.domain.Post;
import honchi.api.domain.post.domain.PostImage;
import honchi.api.domain.post.domain.enums.Category;
import honchi.api.domain.post.domain.enums.Completion;
import honchi.api.domain.post.domain.repository.PostImageRepository;
import honchi.api.domain.post.domain.repository.PostRepository;
import honchi.api.domain.post.dto.*;
import honchi.api.domain.post.exception.PostNotFoundException;
import honchi.api.domain.post.exception.UserNotSameException;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final PostImageRepository postImageRepository;

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

                postImageRepository.save(
                        PostImage.builder()
                                .postId(post.getId())
                                .imageName(imageName)
                                .build()
                );

                file.transferTo(new File(imageDirPath, imageName));
                postImages.add(new PostImage(post.getId(), imageName));
            }
        }

        post.setImage(postImages);

        postRepository.save(post);
    }

    @Override
    public List<PostListResponse> getList(PostListRequest postListRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        if (postListRequest.getLat() != 0.0 && postListRequest.getLon() != 0.0) {
            user.setLat(postListRequest.getLat());
            user.setLon(postListRequest.getLon());

            userRepository.save(user);
        }

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
                .build();
    }

    @SneakyThrows
    @Override
    public void fixPost(Integer postId, PostFixRequest postFixRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!user.getId().equals(post.getUserId()))
            throw new UserNotSameException();

        post.setTitle(postFixRequest.getTitle());
        post.setContent(postFixRequest.getContent());
        post.setCategory(postFixRequest.getCategory());
        post.setItem(postFixRequest.getItem());

        postRepository.save(post);

        List<PostImage> postImages = postImageRepository.findAllByPostId(postId);

        for (PostImage postImage : postImages) {
            new File(imageDirPath, postImage.getImageName()).deleteOnExit();
        }

        postImageRepository.deleteById(postId);

        for (MultipartFile file : postFixRequest.getImages()) {
            String fileName = UUID.randomUUID().toString();
            postImageRepository.save(
                    PostImage.builder()
                            .postId(postId)
                            .imageName(fileName)
                            .build()
            );

            file.transferTo(new File(imageDirPath, fileName));
        }
    }
}