package bank.model.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Entity of usr table
 */
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
    private Long id;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "pass")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "activation_code")
    private String activationCode;

    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(
            name = "usr_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(status, user.status) &&
                Objects.equals(activationCode, user.activationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, status, activationCode);
    }
}
