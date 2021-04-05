package Chat.serverside.service;

import Chat.serverside.interfaces.DBAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final int PORT = 8181;

    private List<ClientHandler> clientsList;

    private DBAuthService dbAuthService;

    public DBAuthService getdbAuthService() {
        return dbAuthService;
    }

    public MyServer() {

        clientsList = new ArrayList<>();

        if (!Database.connect()) {
            throw new RuntimeException("Невозможно подключиться к базе данных.");
        }

        dbAuthService = new DatabaseAuthService();

        try (ServerSocket server = new ServerSocket(PORT)) {

            while (true) {
                System.out.println("Сервер ожидает подключения");
                Socket socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка в работе сервера");
        } finally {
            Database.disconnect();
        }
    }

    public void chatLog(){


    }

    public synchronized void subscribe(ClientHandler c) {
        clientsList.add(c);
    }

    public synchronized void unsubscribe(ClientHandler c) {
        clientsList.remove(c);
    }

    public synchronized boolean isNickBusy(String nick) {

        return clientsList.stream().anyMatch(a -> a.getName().equals(nick));
    }

    public synchronized void sentMsgToClient(String msg, String name) {
        for (ClientHandler c : clientsList) {
            if (!c.getName().equals(name)) {
                c.sendMsg(name + ": " + msg);
                System.out.println(name + ": " + msg);
            } else {
                c.sendMsg(msg);
            }
        }
    }

    public synchronized void sentMsgToClient(ClientHandler clientHandler, String nameTo, String msg) {

        for (ClientHandler c : clientsList) {
            if (c.getName().equalsIgnoreCase(nameTo)) {
                c.sendMsg(clientHandler.getName() + ": приватное сообщение: " + msg);
                clientHandler.sendMsg("Ты написал приватное сообщение " + nameTo + ": " + msg);
                return;
            }
        }
        clientHandler.sendMsg(nameTo + ": оффлайн");
    }

    public synchronized void sendOnlineClientList(ClientHandler clientHandler) {

        clientHandler.sendMsg("Сейчас онлайн: " + clientsList.toString());
    }
}

