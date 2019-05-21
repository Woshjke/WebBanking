package bank.database.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String login;

    @NonNull
    private String password;

    public User() {}

    public User(@NonNull String login, @NonNull String password) {
        this.login = login;
        this.password = password;
    }
}