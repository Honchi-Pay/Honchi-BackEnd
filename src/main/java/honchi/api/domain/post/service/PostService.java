package honchi.api.domain.post.service;

import honchi.api.domain.post.dto.PostContentResponse;
import honchi.api.domain.post.dto.PostListRequest;
import honchi.api.domain.post.dto.PostListResponse;
import honchi.api.domain.post.dto.PostWriteRequest;

import java.util.List;

public interface PostService {

    void write(PostWriteRequest postWriteRequest);
    List<PostListResponse> getList(PostListRequest postListRequest);
    PostContentResponse getContent(Integer postId);
}
