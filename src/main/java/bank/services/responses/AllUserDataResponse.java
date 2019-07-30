package bank.services.responses;

import bank.model.dto.BankAccountDTO;
import bank.model.dto.OrganisationsDTO;
import bank.model.dto.RoleDTO;
import bank.model.dto.UserDTO;
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
    private List<RoleDTO> userRoles;
    private List<OrganisationsDTO> userOrganisations;
}
