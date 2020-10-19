package honchi.api.domain.post.domain;

import honchi.api.domain.post.domain.enums.Category;
import honchi.api.domain.post.domain.enums.Completion;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<PostAttend> postAttends;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Completion completion;

    private LocalDateTime completeAt;
}
