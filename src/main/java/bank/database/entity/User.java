package bank.database.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "usr")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Column(name = "username")
    private String login;

    @NonNull
    @Column(name = "pass")
    private String password;

    @NonNull
    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "money_count")
    private int money_count;

}
