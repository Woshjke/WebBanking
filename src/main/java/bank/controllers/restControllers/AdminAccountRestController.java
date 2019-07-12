package bank.controllers.restControllers;

import bank.database.dto.UserDTO;
import bank.services.dbServices.UserDaoService;
import bank.services.responses.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static bank.PageNameConstants.READ_USERS_PAGE;

@RestController
public class AdminAccountRestController {

    private UserDaoService userDaoService;

    @Autowired
    public AdminAccountRestController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @RequestMapping(value = "/readUsers", method = RequestMethod.GET)
    public ModelAndView readUsers() {
        return new ModelAndView(READ_USERS_PAGE);
    }

    @RequestMapping(value = "/filterUsers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getAllUsers(@RequestParam String myInfo) {
        if (myInfo.equals("")) {
            List<UserDTO> users = userDaoService.getUserDtoList();
            ServiceResponse<List<UserDTO>> response = new ServiceResponse<>("success", users);
            System.out.println(myInfo);
            return ResponseEntity.ok(response);
        } else {
            List<UserDTO> userDTOS = new ArrayList<>();
            userDTOS.add(userDaoService.getUserDtoByUsername(myInfo));
            ServiceResponse<List<UserDTO>> response = new ServiceResponse<>("success", userDTOS);
            System.out.println(myInfo);
            return ResponseEntity.ok(response);
        }
    }

}
