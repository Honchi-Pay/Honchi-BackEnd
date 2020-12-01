package honchi.api.domain.post.service;

import honchi.api.domain.post.domain.enums.Category;
import honchi.api.domain.post.dto.*;

import java.util.List;

public interface PostService {

    Integer write(PostWriteRequest postWriteRequest);
    List<RecentPostListResponse> getRecentList(Category category);
    List<PostListResponse> getList(PostListRequest postListRequest);
    List<PostListResponse> searchPost(PostSearchListRequest postSearchListRequest);
    PostContentResponse getContent(Integer postId);
    List<PostAttendListResponse> getAttendList(Integer postId);
    List<MakeChatResponse> makeChat(Integer postId);
    void updatePoint(UpdatePointRequest updatePointRequest);
    void attendPost(Integer postId);
    void fixPost(Integer postId, PostFixRequest postFixRequest);
    void complete(Integer postId);
    void deletePost(Integer postId);
}
