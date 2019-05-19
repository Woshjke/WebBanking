package bank;

public class User {
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Регистрация пользователя путем занесения в БД
    public boolean register() {
//        if (Database.saveUser(this)) {
//            return true;
//        } else return false;
        return true;
    }

    //Проверяет существует ли такой пользователь
    public boolean isRegistered() {
//        if (Database.findUser(this)) {
//            return true;
//        } else return false;
        return true;
    }

}
