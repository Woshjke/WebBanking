package bank.database.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "money")
    private double money;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void addMoney(Integer money) {
        double targetMoney = getMoney() + money;
        this.setMoney(targetMoney);
    }

    public void takeMoney(Integer money) {
        double targetMoney = getMoney() - money;
        this.setMoney(targetMoney);
    }
}
