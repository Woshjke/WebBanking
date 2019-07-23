package bank.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Entity of organisations table
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "organisations")
public class Organisations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "organisation", fetch = FetchType.EAGER)
    private List<BankAccount> bankAccountList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organisations)) return false;
        Organisations that = (Organisations) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getBankAccountList(), that.getBankAccountList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBankAccountList());
    }
}
