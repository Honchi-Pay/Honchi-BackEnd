package honchi.api.domain.post.service;

import honchi.api.domain.post.domain.enums.Category;
import honchi.api.domain.post.dto.*;

import java.util.List;

public interface PostService {

    void write(PostWriteRequest postWriteRequest);
    List<RecentPostListResponse> getRecentList(Category category);
    List<PostListResponse> getList(PostListRequest postListRequest);
    List<PostListResponse> searchPost(PostSearchListRequest postSearchListRequest);
    PostContentResponse getContent(Integer postId);
    List<PostAttendListResponse> getAttendList(Integer postId);
    List<MakeChatResponse> makeChat(Integer postId);
    List<BuyListResponse> getBuyList();
    void updatePoint(UpdatePointRequest updatePointRequest);
    void attendPost(Integer postId);
    void fixPost(Integer postId, PostFixRequest postFixRequest);
    void deletePost(Integer postId);
}
