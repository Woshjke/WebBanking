package bank.responses;

import bank.model.dto.BankAccountDTO;
import bank.model.dto.OrganisationsDTO;
import bank.model.dto.RoleDTO;
import bank.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class that contains status of response to AJAX, DTOs of user and his bank accounts, roles and organisations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUserDataResponse {
    private String status;
    private UserDTO user;
    private List<BankAccountDTO> userBankAccounts;
    private List<RoleDTO> userRoles;
    private List<OrganisationsDTO> userOrganisations;
}
