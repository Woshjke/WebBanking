package bank.controllers;

import bank.AuthenticationHelper;
import bank.RequestValidator;
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
    private AuthenticationHelper authenticationHelper;
    private RequestValidator validator;

    @Autowired
    public TransactionController(UserAccountService userService,
                                 AuthenticationHelper authenticationHelper,
                                 RequestValidator validator) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.validator = validator;
    }

    /**
     * Handling request of getting transaction page
     * @return transaction page view
     */
    @RequestMapping(value = "/transaction", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showTransactionPage() {
        ModelAndView mnv = new ModelAndView(MONEY_TRANSFER_PAGE);
        User user = authenticationHelper.getAuthenticatedUser();
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
        try {
            validator.isValidTransactionRequest(request);
            Long sourceBankAccountId = Long.valueOf(request.getParameter("source"));
            String destinationBankAccountCardNumber = request.getParameter("destination");
            Integer moneyValue = Integer.valueOf(request.getParameter("value"));
            userService.doTransaction(sourceBankAccountId, destinationBankAccountCardNumber, moneyValue);
            return new RedirectView(USER_PAGE + "?resultMessage=Transaction completed");
        } catch (Exception ex) {
            return new RedirectView(USER_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }
}