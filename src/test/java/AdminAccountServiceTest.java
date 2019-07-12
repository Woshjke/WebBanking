import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static junit.framework.TestCase.assertTrue;

public class AdminAccountServiceTest {

    @Test
    public void encryptTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);
        String passwordHash = bCryptPasswordEncoder.encode("user3");

        boolean isEqual = bCryptPasswordEncoder.matches("user3", passwordHash);
        assertTrue(isEqual);
    }
}
