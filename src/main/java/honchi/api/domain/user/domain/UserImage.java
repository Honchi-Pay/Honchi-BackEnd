package honchi.api.domain.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserImage {

    @Id
    private Integer userId;

    private String imageName;

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
