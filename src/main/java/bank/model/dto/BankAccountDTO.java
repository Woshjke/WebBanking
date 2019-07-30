package bank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for BankAccount entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BankAccountDTO implements Serializable {
    private Long id;
    private Double money;
    private Long user_id;
    private Long organisation_id;

    public BankAccountDTO(Long id, Double money) {
        this.id = id;
        this.money = money;
    }
}
