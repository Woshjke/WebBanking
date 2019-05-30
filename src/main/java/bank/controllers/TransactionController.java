package bank.controllers;

import bank.database.entity.Transaction;
import bank.database.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/doTransaction/{bankAccountNumber}", method = RequestMethod.POST)
    public String goToTransaction(ModelMap map, @PathVariable("bankAccountNumber") long accountNumber) {
        map.addAttribute("accountNumber", accountNumber);
        return "moneyTransfer";
    }

    @RequestMapping(value = "/transaction/{accountNumber}", method = RequestMethod.POST)
    public String doTransaction(ModelAndView map,
                                @PathVariable("accountNumber") long accountNumber,
                                @ModelAttribute("transaction") Transaction transaction
    ) {
        //todo Заменить на что-то нормальное
        transaction.setFrom("12345678");
        transactionService.createTransaction(transaction);
        return "accountPage";
    }

    @ModelAttribute("transaction")
    public Transaction setSignUpForm() {
        return new Transaction();
    }
}
