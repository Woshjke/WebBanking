package bank;

public class User {
    private String name;
    private String password;

    public User(String login, String password) {
        this.name = login;
        this.password = password;
    }

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
