package bank.controllers;

import bank.database.entity.Organisations;
import bank.database.entity.User;
import bank.database.service.OrganisationService;
import bank.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class UserAccountController {

    private final OrganisationService organisationService;
    private final UserService userService;

    @Autowired
    public UserAccountController(OrganisationService organisationService, UserService userService) {
        this.organisationService = organisationService;
        this.userService = userService;
    }


    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payment(ModelMap modelMap) {

        List<Organisations> organisations = organisationService.getOrgs();
        modelMap.addAttribute("orgs", organisations);
        return "paymentPage";
    }

    @RequestMapping(value = "/doPayment", method = RequestMethod.POST)
    public String doPayment(HttpServletRequest request) {
        Long selectedOrg = Long.parseLong(request.getParameter("organisation"));
        Integer moneyToAdd = Integer.parseInt(request.getParameter("money_count"));
        Organisations organisation = organisationService.getOrgById(selectedOrg);
        Long userId = organisation.getUser_id();
        User user = userService.getUserGyId(userId);
        Integer currentMoney = user.getMoney_count();
        user.setMoney_count(currentMoney + moneyToAdd);
        userService.updateUser(user);
        return "userPage";
    }
}
