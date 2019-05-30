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
@Table(name = "mydatabase.transaction")
public class Transaction implements Serializable {

    //todo переделать названия
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Column(name = "fromuser")
    private String from;

    @NonNull
    @Column(name = "touser")
    private String to;

    @NonNull
    @Column(name = "money")
    private String value;
}
