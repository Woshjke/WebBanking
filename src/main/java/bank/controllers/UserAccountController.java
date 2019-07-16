package bank.controllers;

import bank.model.entity.BankAccount;
import bank.model.entity.Organisations;
import bank.model.entity.User;
import bank.model.json.CurrencyRate;
import bank.services.UserAccountService;
import bank.services.dbServices.OrganisationDaoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static bank.PageNameConstants.PAYMENT_PAGE;
import static bank.PageNameConstants.USER_PAGE;


@Controller
@RequestMapping(value = "/user")
public class UserAccountController {

    private OrganisationDaoService organisationService;
    private UserAccountService userAccountService;

    @Autowired
    public UserAccountController(OrganisationDaoService organisationService, UserAccountService userAccountService) {
        this.organisationService = organisationService;
        this.userAccountService = userAccountService;
    }


    @RequestMapping(value = "/user_page", method = RequestMethod.GET)
    public ModelAndView getUserPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(USER_PAGE);

        String resultMessage = request.getParameter("resultMessage");
        if (resultMessage != null) {
            mnv.addObject("resultMessage", resultMessage);
        }

        CurrencyRate usdRate = userAccountService.getCurrencyRate("USD");
        CurrencyRate eurRate = userAccountService.getCurrencyRate("EUR");
        mnv.addObject("usdRate", usdRate);
        mnv.addObject("eurRate", eurRate);

        User authUser = userAccountService.getAuthenticatedUser();
        mnv.addObject("authUser", authUser);

        return mnv;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView redirectToUserPage() {
        return new RedirectView(USER_PAGE);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ModelAndView getPaymentPage() {
        ModelAndView mnv = new ModelAndView(PAYMENT_PAGE);
        List<Organisations> organisations = organisationService.getOrgs();
        mnv.addObject("orgs", organisations);

        User user = userAccountService.getAuthenticatedUser();
        List<BankAccount> bankAccountSet = user.getBankAccounts();
        mnv.addObject("bankAccounts", bankAccountSet);
        return mnv;
    }

    @RequestMapping(value = "/doPayment", method = RequestMethod.POST)
    public RedirectView doPayment(HttpServletRequest request) {
        if (!userAccountService.doPayment(request)) {
            return new RedirectView(USER_PAGE + "?resultMessage=Transaction failed");
        } else {
            return new RedirectView(USER_PAGE + "?resultMessage=Transaction completed");
        }
    }
}
