package bank.controllers;

import bank.AuthenticationHelperService;
import bank.model.entity.BankAccount;
import bank.model.entity.Organisations;
import bank.model.entity.User;
import bank.services.UserAccountService;
import bank.services.dbServices.OrganisationDaoService;
import org.hibernate.exception.LockAcquisitionException;
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
 * Payment to organisation controller.
 */
@Controller
@RequestMapping(value = "/user")
public class PaymentController {

    private OrganisationDaoService organisationService;
    private AuthenticationHelperService authenticationHelperService;
    private UserAccountService userAccountService;

    @Autowired
    public PaymentController(OrganisationDaoService organisationService,
                             AuthenticationHelperService authenticationHelperService,
                             UserAccountService userAccountService) {
        this.organisationService = organisationService;
        this.authenticationHelperService = authenticationHelperService;
        this.userAccountService = userAccountService;
    }

    /**
     * Handling request of getting payment page.
     * @return payment page view.
     */
    @RequestMapping(value = "/payment", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showPaymentPage() {
        ModelAndView mnv = new ModelAndView(PAYMENT_PAGE);
        List<Organisations> organisations = organisationService.getOrganisations();
        mnv.addObject("orgs", organisations);

        User user = authenticationHelperService.getAuthenticatedUser(true);
        if (user == null) {
            mnv.setView(new RedirectView(USER_PAGE + "?resultMessage=User don't have bank account"));
            return mnv;
        }
        List<BankAccount> bankAccountList = user.getBankAccounts();
        mnv.addObject("bankAccounts", bankAccountList);
        return mnv;
    }

    /**
     * Handling payment request and calls payment service.
     * @param request - request from JSP.
     * @return user page view with payment result.
     */
    @RequestMapping(value = "/doPayment", method = RequestMethod.POST)
    public RedirectView doPayment(HttpServletRequest request) {
        try {
            userAccountService.prepareForPayment(request);
            return new RedirectView(USER_PAGE + "?resultMessage=Payment completed");
        } catch (LockAcquisitionException ex) {
            return new RedirectView(USER_PAGE + "?resultMessage=Cannot commit transaction!");
        } catch (Exception ex) {
            return new RedirectView(USER_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }
}
