package honchi.api.domain.user.domain;

import honchi.api.domain.post.domain.Post;
import honchi.api.domain.user.domain.enums.Sex;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 8)
    private String nickName;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne(cascade = CascadeType.ALL)
    private UserImage image;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Post> posts;

    private Double lat;

    private Double lon;

    @Builder
    public User(String email, String password, String nickName, String phoneNumber, Sex sex) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }
}
