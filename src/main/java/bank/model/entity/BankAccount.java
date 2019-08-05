package bank.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of bank_account table
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_account")
public class BankAccount implements Serializable {

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

    @Column(name = "card_number")
    private String cardNumber;

    public void addMoney(Integer money) {
        double targetMoney = getMoney() + money;
        this.setMoney(targetMoney);
    }

    public void takeMoney(Integer money) {
        double targetMoney = getMoney() - money;
        this.setMoney(targetMoney);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getMoney(), that.getMoney()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getOrganisation(), that.getOrganisation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMoney(), getUser(), getOrganisation());
    }
}
