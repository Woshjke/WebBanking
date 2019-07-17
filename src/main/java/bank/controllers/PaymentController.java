package bank.controllers;

import bank.model.entity.BankAccount;
import bank.model.entity.Organisations;
import bank.model.entity.User;
import bank.services.UserAccountService;
import bank.services.dbServices.OrganisationDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static bank.ApplicationProperties.PAYMENT_PAGE;
import static bank.ApplicationProperties.USER_PAGE;

/**
 * user to organization transactions controller
 */
@Controller
@RequestMapping(value = "/user")
public class PaymentController {

    private OrganisationDaoService organisationService;
    private UserAccountService userAccountService;

    @Autowired
    public PaymentController(OrganisationDaoService organisationService, UserAccountService userAccountService) {
        this.organisationService = organisationService;
        this.userAccountService = userAccountService;
    }

    /**
     * Handling request of getting payment page
     * @return payment page view
     */
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ModelAndView getPaymentPage() {
        ModelAndView mnv = new ModelAndView(PAYMENT_PAGE);
        List<Organisations> organisations = organisationService.getOrganisations();
        mnv.addObject("orgs", organisations);

        User user = userAccountService.getAuthenticatedUser();
        List<BankAccount> bankAccountSet = user.getBankAccounts();
        mnv.addObject("bankAccounts", bankAccountSet);
        return mnv;
    }

    /**
     * handling payment request and calls payment service
     * @param request - request from JSP
     * @return user page view with payment result
     */
    @RequestMapping(value = "/doPayment", method = RequestMethod.POST)
    public RedirectView doPayment(HttpServletRequest request) {
        if (!userAccountService.doPayment(request)) {
            return new RedirectView(USER_PAGE + "?resultMessage=Payment failed");
        } else {
            return new RedirectView(USER_PAGE + "?resultMessage=Payment completed");
        }
    }
}
