package honchi.api.domain.chat.domain;

import honchi.api.domain.chat.domain.enums.Authority;
import honchi.api.domain.message.domain.Message;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    private String roomId;

    @Column(nullable = false)
    private Integer postId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private Integer readPoint;

    @OneToMany(mappedBy = "chatId", cascade = CascadeType.ALL)
    private List<Message> messages;

    public Chat updateTitle(String title) {
        this.title = title;

        return this;
    }
}
