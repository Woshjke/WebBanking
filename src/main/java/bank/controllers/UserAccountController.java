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

import static bank.PageNameConstants.PAYMENT_PAGE;
import static bank.PageNameConstants.USER_PAGE;


@Controller
public class UserAccountController {

    private OrganisationDaoService organisationService;
    private UserAccountService userService;

    @Autowired
    public UserAccountController(OrganisationDaoService organisationService, UserAccountService userService) {
        this.organisationService = organisationService;
        this.userService = userService;
    }

    @RequestMapping(value = "/user_page", method = RequestMethod.GET)
    public ModelAndView getUserPage() {
        return new ModelAndView(USER_PAGE);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView redirectToUserPage() {
        return new RedirectView(USER_PAGE);
    }

    //todo Разобраться почему два /payment
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ModelAndView payment() {
        ModelAndView mnv = new ModelAndView(PAYMENT_PAGE);
        List<Organisations> organisations = organisationService.getOrgs();
        mnv.addObject("orgs", organisations);
        return mnv;
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ModelAndView getPaymentPage() {
        ModelAndView mnv = new ModelAndView(PAYMENT_PAGE);
        List<Organisations> organisations = organisationService.getOrgs();
        mnv.addObject("orgs", organisations);

        User user = userService.getAuthenticatedUser();
        List<BankAccount> bankAccountSet = user.getBankAccounts();
        mnv.addObject("bankAccounts", bankAccountSet);
        return mnv;
    }

    @RequestMapping(value = "/doPayment", method = RequestMethod.POST)
    public RedirectView doPayment(HttpServletRequest request) {
        Long selectedOrg = Long.parseLong(request.getParameter("organisation"));
        Integer moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
        Long selectedBankAccount = Long.parseLong(request.getParameter("bankAccounts"));
        userService.doPayment(selectedOrg, moneyToAdd, selectedBankAccount);
        return new RedirectView(USER_PAGE);
    }
}
