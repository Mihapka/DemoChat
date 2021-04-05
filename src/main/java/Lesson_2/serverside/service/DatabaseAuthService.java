package Lesson_2.serverside.service;

import Lesson_2.serverside.interfaces.DBAuthService;

public class DatabaseAuthService implements DBAuthService {

    @Override
    public String getNickname(String login, String password) {
        return Database.getUserNickname(login, password);
    }

    @Override
    public String getId(String login, String password) {
        return Database.getUserId(login, password);
    }

    @Override
    public boolean changeNickname(String currentNickname, String newNickname) {
        return Database.changeUserNickname(currentNickname, newNickname);
    }
}