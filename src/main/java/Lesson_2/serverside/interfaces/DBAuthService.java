package Lesson_2.serverside.interfaces;

public interface DBAuthService {

    String getNickname(String login, String password);

    String getId(String login, String password);

    boolean changeNickname(String currentNickname, String newNickname);
}