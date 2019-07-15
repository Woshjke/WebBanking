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
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    // TODO: 15.07.2019 Провести  эксперемент с синхронизацией базы данных (одновременно два платежа Один запускает в ране, второй в дебаге, одновременно снимают деньги. По идее должен выпасть OptimisticLockException

    // TODO: 15.07.2019 Найти решения проблемы с переводом средств на один аккаунт (вылетает на втором переводе)


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "money")
    private Double money;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organisation_id")
    private Organisations organisation;

    public void addMoney(Integer money) {
        double targetMoney = getMoney() + money;
        this.setMoney(targetMoney);
    }

    public void takeMoney(Integer money) {
        double targetMoney = getMoney() - money;
        this.setMoney(targetMoney);
    }
}
