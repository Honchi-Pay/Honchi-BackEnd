package honchi.api.domain.post.service;

import honchi.api.domain.post.dto.*;

import java.util.List;

public interface PostService {

    void write(PostWriteRequest postWriteRequest);
    List<PostListResponse> getList(PostListRequest postListRequest);
    PostContentResponse getContent(Integer postId);
    void fixPost(Integer postId, PostFixRequest postFixRequest);
}
