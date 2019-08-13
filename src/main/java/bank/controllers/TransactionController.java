package bank.controllers;

import bank.AuthenticationHelperService;
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
 * Money transactions controller.
 */
@Controller
@RequestMapping(value = "/user")
public class TransactionController {

    private UserAccountService userService;
    private AuthenticationHelperService authenticationHelperService;
    private RequestValidator validator;

    @Autowired
    public TransactionController(UserAccountService userService,
                                 AuthenticationHelperService authenticationHelperService,
                                 RequestValidator validator) {
        this.userService = userService;
        this.authenticationHelperService = authenticationHelperService;
        this.validator = validator;
    }

    /**
     * Handling request of getting transaction page.
     * @return transaction page view.
     */
    @RequestMapping(value = "/transaction", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showTransactionPage() {
        ModelAndView mnv = new ModelAndView(MONEY_TRANSFER_PAGE);
        User user = authenticationHelperService.getAuthenticatedUser(true);
        if (user == null) {
            mnv.setView(new RedirectView(USER_PAGE + "?resultMessage=User don't have bank account"));
            return mnv;
        }
        mnv.addObject("bankAccounts", new ArrayList<>(user.getBankAccounts()));
        return mnv;
    }

    /**
     * Handling transaction request and calls transaction service.
     * @param request - request from JSP.
     * @return user page view with transaction result.
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