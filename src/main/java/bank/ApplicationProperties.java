package bank;

import java.io.IOException;
import java.util.Properties;

/**
 * Constants container
 */
public class ApplicationProperties {
    public static final String ADMIN_PAGE = "admin_page";
    public static final String CREATE_USER_PAGE = "create_user_page";
    public static final String DELETE_USER_PAGE = "delete_user_page";
    public static final String HOME_PAGE = "home_page";
    public static final String LOGIN_PAGE = "login_page";
    public static final String MONEY_TRANSFER_PAGE = "money_transfer_page";
    public static final String PAYMENT_PAGE = "payment_page";
    public static final String READ_USERS_PAGE = "read_users_page";
    public static final String UPDATE_USER_PAGE = "update_user_page";
    public static final String USER_PAGE = "user_page";
    public static final String READ_BANK_ACCOUNTS = "read_bank_accounts_page";

    private final Properties properties;


    /**
     * Method loads properties from file.
     */
    public ApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("app.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getProperty(String property) {
        return properties.getProperty(property);
    }

}
