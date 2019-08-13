package bank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for Role entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
}
