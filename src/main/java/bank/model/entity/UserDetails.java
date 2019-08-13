package bank.model.entity;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity class of user_details table
 */
@Entity
@Getter
@Setter
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private String dob;

    @Column(name = "pass_id")
    private String passId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Column(name = "profile_image")
    private byte[] profileImage;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
