package bank.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class HomeController {

//    private UserAccountService userAccountService;
//
//    @Autowired
//    public HomeController(UserAccountService userAccountService) {
//        this.userAccountService = userAccountService;
//    }
//
//    @RequestMapping(value = "/home_page", method = RequestMethod.POST)
//    public ModelAndView postHomepage() {
//        return new ModelAndView(HOME_PAGE);
//    }
//
//
//    @RequestMapping(value = "/home_page", method = RequestMethod.GET)
//    public ModelAndView getHomepage() {
//        ModelAndView mnv = new ModelAndView(HOME_PAGE);
//        CurrencyRate usdRate = userAccountService.getCurrencyRate("USD");
//        CurrencyRate eurRate = userAccountService.getCurrencyRate("EUR");
//        mnv.addObject("usdRate", usdRate);
//        mnv.addObject("eurRate", eurRate);
//        return mnv;
//    }

}
