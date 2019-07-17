package bank.controllers;

import bank.model.entity.User;
import bank.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static bank.ApplicationProperties.MONEY_TRANSFER_PAGE;
import static bank.ApplicationProperties.USER_PAGE;

/**
 * direct bank account-bank account transactions controller
 */
@Controller
@RequestMapping(value = "/user")
public class TransactionController {

    private UserAccountService userService;

    @Autowired
    public TransactionController(UserAccountService userService) {
        this.userService = userService;
    }

    /**
     * Handling request of getting transaction page
     * @return transaction page view
     */
    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public ModelAndView getTransaction() {
        ModelAndView mnv = new ModelAndView(MONEY_TRANSFER_PAGE);
        User user = userService.getAuthenticatedUser();
        mnv.addObject("bankAccounts", new ArrayList<>(user.getBankAccounts()));
        return mnv;
    }

    /**
     * Handling request of getting transaction page, but POST
     * @return transaction page view
     */
    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public ModelAndView postTransaction() {
        ModelAndView mnv = new ModelAndView(MONEY_TRANSFER_PAGE);
        User user = userService.getAuthenticatedUser();
        mnv.addObject("bankAccounts", new ArrayList<>(user.getBankAccounts()));
        return mnv;
    }


    /**
     * handling transaction request and calls transaction service
     * @param request - request from JSP
     * @return user page view with transaction result
     */
    @RequestMapping(value = "/doTransaction", method = RequestMethod.POST)
    public RedirectView doTransaction(HttpServletRequest request) {
        if (!userService.doTransaction(request)) {
            return new RedirectView(USER_PAGE + "?resultMessage=Transaction failed");
        } else {
            return new RedirectView(USER_PAGE + "?resultMessage=Transaction completed");
        }
    }
}
