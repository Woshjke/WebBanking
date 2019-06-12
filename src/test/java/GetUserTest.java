import bank.configuration.FrontControllerConfig;
import bank.configuration.HibernateConfig;
import bank.configuration.WebMvcConfig;
import bank.database.dao.UserDao;
import bank.database.entity.User;
import bank.database.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {HibernateConfig.class, WebMvcConfig.class, FrontControllerConfig.class})
@WebAppConfiguration
public class GetUserTest {

    @Autowired
    UserService userService;

    @Test
    public void testGetUser() {
        List<User> userList = userService.getUsers();
        assertEquals("DB is not empty ?", 2, userList.size());
    }

}
