package bank.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity class of usr_role table
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usr_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;



    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
