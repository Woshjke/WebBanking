package bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static bank.PageNameConstants.MONEY_TRANSFER_PAGE;

@Controller
@RequestMapping("/transfer")
public class MoneyTransferController {

    @RequestMapping(method = RequestMethod.GET)
    public String transferMoney(ModelMap model) {
        return MONEY_TRANSFER_PAGE;
    }
}
