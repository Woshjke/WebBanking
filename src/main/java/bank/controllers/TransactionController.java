package bank.controllers;

import bank.model.entity.Transaction;
import bank.model.entity.User;
import bank.services.UserAccountService;
import bank.services.dbServices.TransactionDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static bank.PageNameConstants.MONEY_TRANSFER_PAGE;
import static bank.PageNameConstants.USER_PAGE;

@Controller
@RequestMapping(value = "/user")
public class TransactionController {

    private UserAccountService userService;



    @Autowired
    public TransactionController(UserAccountService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public ModelAndView getTransaction() {
        ModelAndView mnv = new ModelAndView(MONEY_TRANSFER_PAGE);
        User user = userService.getAuthenticatedUser();
        mnv.addObject("bankAccounts", new ArrayList<>(user.getBankAccounts()));
        return mnv;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public ModelAndView postTransaction() {
        ModelAndView mnv = new ModelAndView(MONEY_TRANSFER_PAGE);
        User user = userService.getAuthenticatedUser();
        mnv.addObject("bankAccounts", new ArrayList<>(user.getBankAccounts()));
        return mnv;
    }


    @RequestMapping(value = "/doTransaction", method = RequestMethod.POST)
    public ModelAndView doTransaction(HttpServletRequest request) {

        userService.doTransaction(request);
        return new ModelAndView(USER_PAGE);
    }

    @ModelAttribute("transaction")
    public Transaction setSignUpForm() {
        return new Transaction();
    }
}
