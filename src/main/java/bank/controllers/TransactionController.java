package bank.controllers;

import bank.database.entity.BankAccount;
import bank.database.entity.Transaction;
import bank.database.entity.User;
import bank.services.UserService;
import bank.services.dbServices.BankAccountService;
import bank.services.dbServices.TransactionDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static bank.PageNameConstants.*;

@Controller
public class TransactionController {

    private TransactionDaoService transactionService;
    private BankAccountService bankAccountService;
    private UserService userService;

    @Autowired
    public TransactionController(TransactionDaoService transactionService, BankAccountService bankAccountService, UserService userService) {
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }


    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String goToTransaction(ModelMap map) {
        User user = userService.getAuthenticatedUser();
        map.addAttribute("bankAccounts", new ArrayList<>(user.getBankAccounts()));
        return MONEY_TRANSFER_PAGE;
    }

    @RequestMapping(value = "/doTransaction", method = RequestMethod.POST)
    public String doTransaction(HttpServletRequest request,
                                @ModelAttribute("transaction") Transaction transaction
    ) {
        transactionService.createTransaction(request);
        return USER_PAGE;
    }

    @ModelAttribute("transaction")
    public Transaction setSignUpForm() {
        return new Transaction();
    }
}
