package honchi.api.domain.post.controller;

import honchi.api.domain.post.dto.PostContentResponse;
import honchi.api.domain.post.dto.PostListRequest;
import honchi.api.domain.post.dto.PostListResponse;
import honchi.api.domain.post.dto.PostWriteRequest;
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

    @GetMapping("/{postId}")
    public PostContentResponse getContent(@PathVariable Integer postId) {
        return postService.getContent(postId);
    }
}
