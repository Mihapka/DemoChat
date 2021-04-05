package Lesson_2.serverside.service;

import Lesson_2.serverside.interfaces.DBAuthService;

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

    public synchronized void subscribe(ClientHandler c) {
        clientsList.add(c);
    }

    public synchronized void unsubscribe(ClientHandler c) {
        clientsList.remove(c);
    }

    public synchronized boolean isNickBusy(String nick) {

        return clientsList.stream().anyMatch(a -> a.getName().equals(nick));
    }

    /* сообщение в чат*/
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

    /* лог чата*/
    public synchronized void sentMsgToClient(String msgLog, String nameTo, String name) {

        for (ClientHandler c : clientsList) {
            if (c.getName().equals(nameTo)) {
                c.sendMsg("Лог чата:" + "\n" + msgLog);
                System.out.println("Лог чата " + name + ": " + msgLog);
            }
        }
    }

    /* сообщение в приватный чат*/
    public synchronized void sentMsgToClient(ClientHandler clientHandler, String nameTo, String msg) {

        for (ClientHandler c : clientsList) {
            if (c.getName().equalsIgnoreCase(nameTo)) {
                c.sendMsg(clientHandler.getName() + " прислал вам приватное сообщение: " + "\n" + msg);
                clientHandler.sendMsg("Ты написал приватное сообщение для " + nameTo + ": " + "\n" + msg);
                return;
            }
        }
        clientHandler.sendMsg(nameTo + ": оффлайн");
    }

    public synchronized void sendOnlineClientList(ClientHandler clientHandler) {

        clientHandler.sendMsg("Сейчас онлайн: " + clientsList.toString());
    }
}

