package bank.services.responses;

import bank.model.dto.BankAccountDTO;
import bank.model.dto.UserDTO;
import bank.model.entity.Organisations;
import bank.model.entity.Role;
import bank.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AllUserDataResponse {
    private String status;
    private UserDTO user;
    private List<BankAccountDTO> userBankAccounts;
    private List<Role> userRoles;
    private List<Organisations> userOrganisations;
}
