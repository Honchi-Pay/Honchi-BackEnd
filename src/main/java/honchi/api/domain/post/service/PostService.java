package honchi.api.domain.post.service;

import honchi.api.domain.post.dto.*;

import java.util.List;

public interface PostService {

    void write(PostWriteRequest postWriteRequest);
    List<PostListResponse> getList(PostListRequest postListRequest);
    List<PostListResponse> getSearch(PostSearchListRequest postSearchListRequest);
    PostContentResponse getContent(Integer postId);
    void attendPost(Integer postId);
    void fixPost(Integer postId, PostFixRequest postFixRequest);
    void deletePost(Integer postId);
}
