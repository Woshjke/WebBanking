package bank.database.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    // TODO: 29.06.2019 Прявязать source и destination к BankAccount

    @Column(name = "source")
    private long source;

    @Column(name = "destination")
    private long destination;

    @Column(name = "val")
    private int value;

    public Transaction(long source, long destination, int value) {
        this.source = source;
        this.destination = destination;
        this.value = value;
    }
}
