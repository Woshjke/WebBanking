package bank.database.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "transaction")
public class Transaction implements Serializable {

    //todo переделать названия
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Column(name = "from_user")
    private String from;

    @NonNull
    @Column(name = "to_user")
    private String to;

    @NonNull
    @Column(name = "money_count")
    private String value;
}
