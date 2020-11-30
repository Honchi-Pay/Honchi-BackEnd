package honchi.api.domain.post.domain;

import honchi.api.domain.post.domain.enums.Category;
import honchi.api.domain.post.domain.enums.Completion;
import honchi.api.domain.post.dto.PostFixRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String item;

    @Column(nullable = false)
    private Double lon;

    @Column(nullable = false)
    private Double lat;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    @OrderColumn
    private List<PostImage> image;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Completion completion;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<PostAttend> postAttends;

    public Post setImage(List<PostImage> postImages) {
        this.image = postImages;

        return this;
    }

    public Post updateContent(PostFixRequest postFixRequest) {
        this.title = postFixRequest.getTitle();
        this.content = postFixRequest.getContent();
        this.category = postFixRequest.getCategory();
        this.item = postFixRequest.getItem();

        return this;
    }

    public Post complete() {
        this.completion = Completion.COMPLETION;

        return this;
    }
}
