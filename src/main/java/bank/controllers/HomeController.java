package bank.controllers;

import bank.model.json.CurrencyRate;
import com.google.gson.Gson;
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
public class HomeController {

    private static final String NBRB_USD_RATE_URL = "http://www.nbrb.by/API/ExRates/Rates/USD?ParamMode=2";

    @RequestMapping(value = "/home_page", method = RequestMethod.POST)
    public String postHomepage() {
        return HOME_PAGE;
    }


    @RequestMapping(value = "/home_page", method = RequestMethod.GET)
    public ModelAndView getHomepage() {
        ModelAndView mnv = new ModelAndView(HOME_PAGE);
        URL urlObj;
        try {
            urlObj = new URL(null, NBRB_USD_RATE_URL, new sun.net.www.protocol.https.Handler());
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            InputStream response = connection.getInputStream();
            String json = new Scanner(response, "UTF-8").nextLine();

            Gson gson = new Gson();
            CurrencyRate currencyRate = gson.fromJson(json, CurrencyRate.class);
            mnv.addObject("USDCurRate", currencyRate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mnv;
    }

}
