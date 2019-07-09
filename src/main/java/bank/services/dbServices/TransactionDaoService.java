package bank.services.dbServices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionDaoService {

//    private TransactionDaoService transactionDaoService;
//    private UserDaoService userDaoService;
//    private BankAccountService bankAccountService;
//
//    @Autowired
//    public TransactionDaoService(TransactionDaoService transactionDaoService, UserDaoService userDaoService, BankAccountService bankAccountService) {
//        this.transactionDaoService = transactionDaoService;
//        this.userDaoService = userDaoService;
//        this.bankAccountService = bankAccountService;
//    }
//
//    public void createTransaction(HttpServletRequest request) {
//        BankAccount sourceBankAccount = bankAccountService.getBankAccountById(Long.parseLong(request.getParameter("source")));
//        // TODO: 29.06.2019 Запилить проаерку на существование destination-счета
//        BankAccount destinationBankAccount = bankAccountService.getBankAccountById(Long.parseLong(request.getParameter("destination")));
//        Integer money_value = Integer.parseInt(request.getParameter("value"));
//        sourceBankAccount.takeMoney(money_value);
//        destinationBankAccount.addMoney(money_value);
//
//        bankAccountService.updateBankAccount(sourceBankAccount);
//        bankAccountService.updateBankAccount(destinationBankAccount);
//
//        //Transaction transaction = new Transaction(source.getId(), destination.getId(), money_value);
//        //transactionDao.createTransaction(transaction);
//    }
}
