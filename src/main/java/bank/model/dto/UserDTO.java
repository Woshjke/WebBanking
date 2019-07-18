package bank.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for User entity
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
}
