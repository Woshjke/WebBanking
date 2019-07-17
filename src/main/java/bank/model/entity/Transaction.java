package bank.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity of transaction table
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source")
    private BankAccount source;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination")
    private BankAccount destination;

    @Column(name = "val")
    private Integer value;

    public Transaction(BankAccount source, BankAccount destination, int value) {
        this.source = source;
        this.destination = destination;
        this.value = value;
    }
}
