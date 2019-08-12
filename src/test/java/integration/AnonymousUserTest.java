package integration;

import bank.configuration.FrontControllerConfig;
import bank.configuration.WebMvcConfig;
import bank.services.AdminAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.net.URI;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FrontControllerConfig.class, WebMvcConfig.class})
@TestPropertySource("/app.properties")
@AutoConfigureMockMvc
public class AnonymousUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLoginPageTest() throws Exception {
        this.mockMvc.perform(get("/admin/admin_page"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void authTest() throws Exception {
        mockMvc.perform(formLogin().user("user").password("user"))
                .andExpect(status().isOk());
    }
}
