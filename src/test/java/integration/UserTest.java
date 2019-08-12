package integration;

import bank.configuration.FrontControllerConfig;
import bank.configuration.WebMvcConfig;
import bank.controllers.UserAccountController;
import bank.model.entity.BankAccount;
import bank.services.UserAccountService;
import bank.services.dbServices.BankAccountDaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FrontControllerConfig.class, WebMvcConfig.class})
@AutoConfigureMockMvc
@WithUserDetails(value = "user", userDetailsServiceBeanName = "userDetailsServiceImpl")
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAdminPageForbidden() throws Exception {
        this.mockMvc.perform(get("/admin/admin_page"))
                .andExpect(status().isForbidden());
    }
}
