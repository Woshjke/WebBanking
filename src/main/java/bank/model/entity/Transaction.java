package bank.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source")
    private BankAccount source;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination")
    private BankAccount destination;

    @Column(name = "val")
    private int value;
}
