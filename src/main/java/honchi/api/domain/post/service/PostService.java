package honchi.api.domain.post.service;

import honchi.api.domain.post.dto.PostWriteRequest;

public interface PostService {

    void write(PostWriteRequest postWriteRequest);
}
