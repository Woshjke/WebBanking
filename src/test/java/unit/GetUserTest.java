package unit;

import bank.configuration.FrontControllerConfig;
import bank.configuration.HibernateConfig;
import bank.configuration.WebMvcConfig;
import bank.services.UserAccountService;
import bank.services.dbServices.UserDaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {HibernateConfig.class, WebMvcConfig.class, FrontControllerConfig.class})
@WebAppConfiguration
public class GetUserTest {

    @Mock
    private UserAccountService service;

    @Autowired
    UserDaoService userService;

    @Test
    public void testGetUser() {

    }

}
