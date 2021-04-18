package Chat.serverside.service;

import Chat.serverside.interfaces.AuthServic;
import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthServic {


    private List<User> userList;

    public AuthServiceImpl() {

        userList = new ArrayList<>();
        userList.add(new User("a", "a", "a"));
        userList.add(new User("b", "b", "b"));
        userList.add(new User("c", "c", "c"));
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) {

        for (User u : userList) {
            if (u.login.equals(login) && u.pass.equals(pass)) return u.nick;
        }
        return "";
    }

    private class User {

        private String login;
        private String pass;
        private String nick;

        public User(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }
}
