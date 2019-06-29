package bank.services.dbServices;

import bank.database.dao.TransactionDao;
import bank.database.entity.BankAccount;
import bank.database.entity.Transaction;
import bank.database.entity.User;
import bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class TransactionDaoService {

    private TransactionDao transactionDao;
    private UserDaoService userDaoService;
    private BankAccountService bankAccountService;

    @Autowired
    public TransactionDaoService(TransactionDao transactionDao, UserDaoService userDaoService, BankAccountService bankAccountService) {
        this.transactionDao = transactionDao;
        this.userDaoService = userDaoService;
        this.bankAccountService = bankAccountService;
    }

    public void createTransaction(HttpServletRequest request) {
        BankAccount source = bankAccountService.getBankAccountById(Long.parseLong(request.getParameter("source")));
        BankAccount destination = bankAccountService.getBankAccountById(Long.parseLong(request.getParameter("destination")));
        Integer money_value = Integer.parseInt(request.getParameter("value"));
        source.takeMoney(money_value);
        destination.addMoney(money_value);

        bankAccountService.updateBankAccount(source);
        bankAccountService.updateBankAccount(destination);

        //Transaction transaction = new Transaction(source.getId(), destination.getId(), money_value);
        //transactionDao.createTransaction(transaction);
    }
}
