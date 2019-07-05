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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "money")
    private Integer money;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void addMoney(Integer money) {
        int targetMoney = getMoney() + money;
        this.setMoney(targetMoney);
    }

    public void takeMoney(Integer money) {
        int targetMoney = getMoney() - money;
        this.setMoney(targetMoney);
    }
}
