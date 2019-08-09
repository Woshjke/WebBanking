package bank.model.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of transaction table
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "source")
    private BankAccount source;

    @OneToOne
    @JoinColumn(name = "destination")
    private BankAccount destination;

    @Column(name = "val")
    private Integer value;

    public Transaction(BankAccount source, BankAccount destination, Integer value) {
        this.source = source;
        this.destination = destination;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }


}
