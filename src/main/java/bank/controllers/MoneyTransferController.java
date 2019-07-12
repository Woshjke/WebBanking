package bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static bank.PageNameConstants.MONEY_TRANSFER_PAGE;

@Controller
@RequestMapping("/transfer")
public class MoneyTransferController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView transferMoney() {
        return new ModelAndView(MONEY_TRANSFER_PAGE);
    }
}
