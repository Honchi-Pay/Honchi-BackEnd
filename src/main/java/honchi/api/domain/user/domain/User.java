package honchi.api.domain.user.domain;

import honchi.api.domain.user.domain.enums.Gender;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    private String nick_name;

    @Column(nullable = false)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer star;

    @Builder
    public User(String email, String password, String nick_name, String phone_number, Gender gender, Integer star) {
        this.email = email;
        this.password = password;
        this.nick_name = nick_name;
        this.phone_number = phone_number;
        this.gender = gender;
        this.star = star;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}
