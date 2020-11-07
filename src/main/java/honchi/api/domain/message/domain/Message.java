package honchi.api.domain.message.domain;

import honchi.api.domain.message.domain.enums.MessageType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String roomId;

    private String message;

    private MessageType messageType;

    private LocalDateTime time;

    private boolean isShow;

    private boolean isDelete;
}