package honchi.api.domain.chat.domain;

import honchi.api.domain.chat.domain.enums.Authority;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String chatId;

    @Column(nullable = false)
    private Integer postId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private Integer readPoint;

    public Chat updateTitle(String title) {
        this.title = title;

        return this;
    }

    public Chat updateRead(Integer readPoint) {
        this.readPoint = readPoint;

        return this;
    }
}
