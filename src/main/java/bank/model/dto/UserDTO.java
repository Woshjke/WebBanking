package bank.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for User entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
}
