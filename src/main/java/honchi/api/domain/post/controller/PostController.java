package honchi.api.domain.post.controller;

import honchi.api.domain.post.dto.*;
import honchi.api.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public void write(@ModelAttribute @Valid PostWriteRequest postWriteRequest) {
        postService.write(postWriteRequest);
    }

    @GetMapping
    public List<PostListResponse> getList(@RequestBody @Valid PostListRequest postListRequest) {
        return postService.getList(postListRequest);
    }

    @GetMapping("/search")
    public List<PostListResponse> getSearch(@RequestBody @Valid PostSearchListRequest postSearchListRequest) {
        return postService.getSearch(postSearchListRequest);
    }

    @GetMapping("/{postId}")
    public PostContentResponse getContent(@PathVariable Integer postId) {
        return postService.getContent(postId);
    }

    @PutMapping("/{postId}/attend")
    public void attendPost(@PathVariable Integer postId) {
        postService.attendPost(postId);
    }

    @PutMapping("/{postId}")
    public void fixPost(@ModelAttribute @Valid PostFixRequest postFixRequest,
                        @PathVariable Integer postId) {
        postService.fixPost(postId, postFixRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
    }
}
