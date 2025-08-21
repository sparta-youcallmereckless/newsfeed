package hello.newsfeed.profile.entity;

import hello.newsfeed.common.entity.BaseEntity;
import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true, nullable = false) // nickname 중복 방지
    private String nickname;
    @Column(unique = true, nullable = false) // email 중복 방지
    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Profile(String username, String nickname, String email, String password, User user) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public void updateProfile(String nickname) {
        this.nickname = nickname;
    }

}
