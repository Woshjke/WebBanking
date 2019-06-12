package bank.controllers;

import bank.database.entity.Organisations;
import bank.database.service.OrganisationService;
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

    @Autowired
    public UserAccountController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payment(ModelMap modelMap) {

        List<Organisations> organisations = organisationService.getOrgs();
        modelMap.addAttribute("orgs", organisations);
        return "paymentPage";
    }

    @RequestMapping(value = "/doPayment", method = RequestMethod.POST)
    public void doPayment(HttpServletRequest request) {
        Integer selectedOrg = Integer.parseInt(request.getParameter("organisation"));
        Integer moneyCount = Integer.parseInt(request.getParameter("money_count"));
        //todo написать запрос с джойнами и всем таким
    }
}
