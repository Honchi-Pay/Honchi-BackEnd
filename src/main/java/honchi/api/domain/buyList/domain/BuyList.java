package honchi.api.domain.buyList.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer postId;

    @Column(nullable = false)
    private Integer price;

    private LocalDateTime time;

    public BuyList updateTime() {
        this.time = LocalDateTime.now();

        return this;
    }
}
