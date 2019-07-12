package bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;

import static bank.PageNameConstants.HOME_PAGE;

@Controller
public class HomeController {

    private static final String NBRB_URL = "http://www.nbrb.by/API/";

    @RequestMapping(value = "/home_page", method = RequestMethod.POST)
    public String postHomepage() {
        return HOME_PAGE;
    }


    // TODO: 07.07.2019 Сделать курс валют
    @RequestMapping(value = "/home_page", method = RequestMethod.GET)
    public ModelAndView getHomepage() {
        URL urlObj = null;
        try {
            urlObj = new URL(NBRB_URL);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = null;
            dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes("something");
            InputStream response = connection.getInputStream();
            String json = new java.util.Scanner(response).nextLine();
            //TranslatorMapper reader = new TranslatorMapper();
            //return (TranslatorResponse) reader.map(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView(HOME_PAGE);
    }

}
