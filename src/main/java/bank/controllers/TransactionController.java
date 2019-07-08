package bank.controllers;

import bank.database.entity.Transaction;
import bank.database.entity.User;
import bank.services.UserService;
import bank.services.dbServices.BankAccountService;
import bank.services.dbServices.TransactionDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static bank.PageNameConstants.MONEY_TRANSFER_PAGE;
import static bank.PageNameConstants.USER_PAGE;

@Controller
public class TransactionController {

    private TransactionDaoService transactionService;
    private UserService userService;

    @Autowired
    public TransactionController(TransactionDaoService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }


    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String goToTransaction(ModelMap map) {
        User user = userService.getAuthenticatedUser();
        map.addAttribute("bankAccounts", new ArrayList<>(user.getBankAccounts()));
        return MONEY_TRANSFER_PAGE;
    }

    @RequestMapping(value = "/doTransaction", method = RequestMethod.POST)
    public String doTransaction(HttpServletRequest request) {
        transactionService.createTransaction(request);
        return USER_PAGE;
    }

    @ModelAttribute("transaction")
    public Transaction setSignUpForm() {
        return new Transaction();
    }
}
