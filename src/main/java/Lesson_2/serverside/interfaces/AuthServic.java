package Lesson_2.serverside.interfaces;

public interface AuthServic {

    void start();
    void stop();
    String getNickByLoginPass(String login, String pass);
}

