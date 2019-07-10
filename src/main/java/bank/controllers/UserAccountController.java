package bank.controllers;

import bank.database.entity.BankAccount;
import bank.database.entity.Organisations;
import bank.database.entity.User;
import bank.services.UserService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import static bank.PageNameConstants.PAYMENT_PAGE;
import static bank.PageNameConstants.USER_PAGE;


@Controller
public class UserAccountController {

    private OrganisationDaoService organisationService;
    private UserService userService;

    @Autowired
    public UserAccountController(OrganisationDaoService organisationService, UserService userService) {
        this.organisationService = organisationService;
        this.userService = userService;
    }

    @RequestMapping(value = "/user_page", method = RequestMethod.GET)
    public String getUserPage() {
        return USER_PAGE;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView redirectToUserPage() {
        return new RedirectView(USER_PAGE);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payment(ModelMap modelMap) {
        List<Organisations> organisations = organisationService.getOrgs();
        modelMap.addAttribute("orgs", organisations);
        return PAYMENT_PAGE;
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String getPaymentPage(ModelMap modelMap) {
        List<Organisations> organisations = organisationService.getOrgs();
        modelMap.addAttribute("orgs", organisations);

        User user = userService.getAuthenticatedUser();
        List<BankAccount> bankAccountSet = user.getBankAccounts();
        modelMap.addAttribute("bankAccounts", bankAccountSet);
        return PAYMENT_PAGE;
    }

    @RequestMapping(value = "/doPayment", method = RequestMethod.POST)
    public String doPayment(HttpServletRequest request) {
        Long selectedOrg = Long.parseLong(request.getParameter("organisation"));
        Integer moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
        Long selectedBankAccount = Long.parseLong(request.getParameter("bankAccounts"));
        userService.doPayment(selectedOrg, moneyToAdd, selectedBankAccount);
        return USER_PAGE;
    }
}
