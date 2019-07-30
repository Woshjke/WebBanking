package integration;

import bank.configuration.FrontControllerConfig;
import bank.configuration.WebMvcConfig;
import bank.controllers.AdminAccountController;
import bank.model.entity.User;
import bank.services.dbServices.UserDaoService;
import org.hibernate.annotations.SQLDeleteAll;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FrontControllerConfig.class, WebMvcConfig.class})
@AutoConfigureMockMvc
@TestPropertySource("/app.properties")
@WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsServiceImpl")
public class AdminTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDaoService userDaoService;

    @Test
    public void getAdminPageSuccess() throws Exception {
        this.mockMvc.perform(get("/admin/admin_page"))
                .andExpect(status().isOk());
    }

    @Test
    public void getReadeUsersPage() throws Exception {
        this.mockMvc.perform(get("/admin/readUsers"))
                .andExpect(status().isOk());
    }

    @Test
    public void getDeleteUserPage() throws Exception {
        this.mockMvc.perform(get("/admin/deleteUser"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCreateUserPage() throws Exception {
        this.mockMvc.perform(get("/admin/createUser"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUpdateUserPage() throws Exception {
        this.mockMvc.perform(get("/admin/updateUser"))
                .andExpect(status().isOk());
    }

    @Test
    public void goodPaymentRequest() throws Exception {
        this.mockMvc.perform(post("/user/doPayment")
                .param("organisation", "1")
                .param("money_count", "1")
                .param("bankAccounts", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("user_page?resultMessage=Payment completed"));
    }

    @Test
    public void PaymentRequestWithBadMoneyCountParam() throws Exception {
        this.mockMvc.perform(post("/user/doPayment")
                .param("organisation", "1")
                .param("money_count", "bad info")
                .param("bankAccounts", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("user_page?resultMessage=Payment failed! Bad request parameters!"));
    }

    @Test
    public void PaymentRequestWithBadOrganisationParam() throws Exception {
        String fakeOrganisationId = "567567";
        this.mockMvc.perform(post("/user/doPayment")
                .param("organisation", fakeOrganisationId)
                .param("money_count", "300")
                .param("bankAccounts", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("user_page?resultMessage=Payment failed!" +
                        " Cannot find organisation with id: " + fakeOrganisationId));
    }

    @Test
    public void PaymentRequestWithEmptyParams() throws Exception {
        this.mockMvc.perform(post("/user/doPayment"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("user_page?resultMessage=Payment failed! Bad request parameters!"));
    }

    @Test
    public void PaymentRequestWithBadBankAccountParam() throws Exception {
        String fakeBankAccountId = "457567567";
        this.mockMvc.perform(post("/user/doPayment")
                .param("organisation", "1")
                .param("money_count", "300")
                .param("bankAccounts", fakeBankAccountId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("user_page?resultMessage=Payment failed! Cannot find source bank account " +
                        "with id: " + fakeBankAccountId));
    }

    @Test
    public void showAllUsers() throws Exception {
        List<User> users = userDaoService.getUsers();
        this.mockMvc.perform(get("/admin/filterUsers?username="))
                .andExpect(jsonPath("$", hasSize(users.size())));
    }

    @Test
    public void showSingleUser() throws Exception {
        String username = userDaoService.getUsers().get(0).getUsername();
        this.mockMvc.perform(get("/admin/filterUsers?username=" + username))
                .andExpect(jsonPath("$", hasSize(1)));
    }


}

