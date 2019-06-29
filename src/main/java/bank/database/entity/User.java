package bank.database.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Table(name = "usr")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "pass")
    private String password;

    @NonNull
    @Column(name = "is_admin")
    private boolean isAdmin;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    //todo-какая-то срань, ничего не понятно
    //@JoinTable(name = "bank_account")
    //@JoinColumn(name = "user_id")
    private Set<BankAccount> bankAccounts;

}
