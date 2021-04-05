package Chat.serverside.interfaces;

public interface DBAuthService {

    String getNickname(String login, String password);

    boolean changeNickname(String currentNickname, String newNickname);
}