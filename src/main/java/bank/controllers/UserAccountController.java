package bank.controllers;

import bank.model.entity.User;
import bank.model.json.CurrencyRate;
import bank.services.UserAccountService;
import bank.services.dbServices.OrganisationDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import static bank.ApplicationProperties.USER_PAGE;

/**
 * User features controller
 */
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

    /**
     * Handling request of getting user page, adding currency rate, needed messages.
     * @param request - request from JSP with messages of completed/failed transaction, etc.
     * @return user page view
     */
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

    /**
     * Redirection empty url to user page
     * @return redirect to user page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView redirectToUserPage() {
        return new RedirectView(USER_PAGE);
    }
}
