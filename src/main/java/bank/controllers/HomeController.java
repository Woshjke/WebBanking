package bank.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static bank.PageNameConstants.*;

@Controller
public class HomeController {

    public static final String NBRB_URL = "http://www.nbrb.by/API/";


    @RequestMapping(value = "/home_page", method = RequestMethod.GET)
    public String getHomepage() {
//        URL urlObj = null;
//        try {
//            urlObj = new URL(NBRB_URL);
//            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            DataOutputStream dataOutputStream = null;
//            dataOutputStream = new DataOutputStream(connection.getOutputStream());
//            dataOutputStream.writeBytes("something");
//            InputStream response = connection.getInputStream();
//            String json = new java.util.Scanner(response).nextLine();
//            //TranslatorMapper reader = new TranslatorMapper();
//            //return (TranslatorResponse) reader.map(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return HOME_PAGE;
    }

    @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET)
    public @ResponseBody
    String getTime() {

        Random rand = new Random();
        float r = rand.nextFloat() * 100;
        String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
        System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
        return result;
    }

    @RequestMapping(value = "/home_page", method = RequestMethod.POST)
    public String postHomepage() {
        return HOME_PAGE;
    }

    public class AjaxResponseBody {
        String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
