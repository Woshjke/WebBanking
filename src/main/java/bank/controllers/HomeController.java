package bank.controllers;

import bank.model.json.CurrencyRate;
import bank.services.UserAccountService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import static bank.PageNameConstants.HOME_PAGE;

@RestController
@RequestMapping(value = "/user")
public class HomeController {

    private UserAccountService userAccountService;

    @Autowired
    public HomeController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "/home_page", method = RequestMethod.POST)
    public ModelAndView postHomepage() {
        return new ModelAndView(HOME_PAGE);
    }


    @RequestMapping(value = "/home_page", method = RequestMethod.GET)
    public ModelAndView getHomepage() {
        ModelAndView mnv = new ModelAndView(HOME_PAGE);
        CurrencyRate usdRate = userAccountService.getCurrencyRate("USD");
        CurrencyRate eurRate = userAccountService.getCurrencyRate("EUR");
        mnv.addObject("usdRate", usdRate);
        mnv.addObject("eurRate", eurRate);
        return mnv;
    }

}
