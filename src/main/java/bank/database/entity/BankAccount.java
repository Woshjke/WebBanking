package bank.database.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "money")
    private Integer money;

    @ManyToOne
    //todo-какая-то срань, ничего не понятно
    //@JoinTable(name = "usr")
    //@JoinColumn(name = "user_id")
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
