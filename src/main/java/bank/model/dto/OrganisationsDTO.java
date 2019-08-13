package bank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for Organisation entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrganisationsDTO {
    private Long id;
    private String name;
}
