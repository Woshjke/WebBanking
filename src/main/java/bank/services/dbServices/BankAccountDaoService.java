package bank.services.dbServices;

import bank.model.dao.BankAccountRepository;
import bank.model.dto.BankAccountDTO;
import bank.model.entity.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountDaoService {

    private final BankAccountRepository bankAccountRepository;
    private final UserDaoService userDaoService;

    @Autowired
    public BankAccountDaoService(BankAccountRepository bankAccountRepository, UserDaoService userDaoService) {
        this.bankAccountRepository = bankAccountRepository;
        this.userDaoService = userDaoService;
    }


    /**
     * Saving/updating bank account to database
     * @param bankAccount - bank account to save/update
     */
    public void saveBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    /**
     * Getting bank accounts for database
     * @return list of bank accounts
     */
    public List<BankAccount> getAllBankAccounts() {
        return (List<BankAccount>) bankAccountRepository.findAll();
    }

    /**
     * Getting bank account form database by ID
     * @param id - bank account ID
     * @return bank account object
     */
    public BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    /**
     * Getting bank accounts of specific user
     * @param id - user id
     * @return list of user bank accounts
     */
    public List<BankAccount> getBankAccountsByUserId(long id) {
        return bankAccountRepository.getAllByUserId(id);
    }

    /**
     * Updating bank account in database
     * @param bankAccount - bank account to update
     */
    public void updateBankAccount(BankAccount bankAccount) {
        //todo need to be deleted
        bankAccountRepository.save(bankAccount);
    }

    /**
     * Deleting bank account from database
     * @param bankAccount - bank account to delete
     */
    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccountRepository.delete(bankAccount);
    }

    /**
     * This method getting list of bank accounts form database and converting it to list of bank accounts DTOs
     * @return list of bank accounts DTOs
     */
    public List<BankAccountDTO> getBankAccountDTOList() {
        List<BankAccount> bankAccounts = (List<BankAccount>) bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                        i.getMoney()))
                .collect(Collectors.toList());
    }

    /**
     * This method getting list of bank accounts form database and converting it to list of bank accounts DTOs
     * @return list of bank accounts DTOs
     */
    public List<BankAccountDTO> getUserBankAccountDTOS(long userId){
        List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(userId);
        return bankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                        i.getMoney()))
                .collect(Collectors.toList());
    }
}
