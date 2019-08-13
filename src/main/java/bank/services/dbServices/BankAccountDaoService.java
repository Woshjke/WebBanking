package bank.services.dbServices;

import bank.model.dto.BankAccountDTO;
import bank.model.entity.BankAccount;
import bank.model.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that performs calls to BankAccount repository.
 */
@Service
@Transactional
public class BankAccountDaoService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountDaoService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Saving/updating bank account to database.
     *
     * @param bankAccount - bank account to save/update.
     */
    public void saveBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    /**
     * Getting bank accounts for database.
     *
     * @return list of bank accounts.
     */
    public List<BankAccount> getAllBankAccounts() {
        return (List<BankAccount>) bankAccountRepository.findAll();
    }

    /**
     * Getting bank account form database by ID.
     *
     * @param id - bank account ID.
     * @return bank account object.
     */
    public BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public BankAccount getBankAccountByCardNumber(String cardNumber) {
        return bankAccountRepository.findByCardNumber(cardNumber);
    }

    /**
     * Getting bank accounts of specific user.
     *
     * @param id - user id.
     * @return list of user bank accounts.
     */
    public List<BankAccount> getBankAccountsByUserId(long id) {
        return bankAccountRepository.getAllByUserId(id);
    }

    /**
     * Updating bank account in database.
     *
     * @param bankAccount - bank account to update.
     */
    public void updateBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    /**
     * Deleting bank account from database.
     *
     * @param bankAccount - bank account to delete.
     */
    public void deleteBankAccount(BankAccount bankAccount) {
        bankAccountRepository.delete(bankAccount);
    }

    /**
     * This method getting list of bank accounts form database and converting it to list of bank accounts DTOs.
     *
     * @return list of bank accounts DTOs.
     */
    public List<BankAccountDTO> getBankAccountDTOList() {
        List<BankAccount> bankAccounts = (List<BankAccount>) bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                        i.getMoney()))
                .collect(Collectors.toList());
    }

    /**
     * This method getting bank account by ID from database and transforming it to bank account DTO.
     * @param id bank account ID;
     * @return bank account DTO
     */
    public BankAccountDTO getBankAccountDTOById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id);
        return new BankAccountDTO(bankAccount.getId(), bankAccount.getMoney());
    }

    /**
     * This method getting list of bank accounts form database and converting it to list of bank accounts DTOs.
     *
     * @return list of bank accounts DTOs.
     */
    public List<BankAccountDTO> getUserBankAccountDTOS(long userId) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(userId);
        return bankAccounts.stream()
                .map(i -> new BankAccountDTO(i.getId(),
                        i.getMoney()))
                .collect(Collectors.toList());
    }
}
