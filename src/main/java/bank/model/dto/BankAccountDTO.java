package bank.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for BankAccount entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class BankAccountDTO {
    private Long id;
    private Double money;
    private Long user_id;
    private Long organisation_id;

    public BankAccountDTO(Long id, Double money) {
        this.id = id;
        this.money = money;
    }
}
