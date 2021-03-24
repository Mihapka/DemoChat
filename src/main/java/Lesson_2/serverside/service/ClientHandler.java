package Lesson_2.serverside.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String name;

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    authentication();
                    readMsg();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authentication() throws IOException {
        while (true) {
            String authStr = dis.readUTF();
            if (authStr.startsWith("/auth")) {
                String[] parts = authStr.split("\\s");
                String nick = myServer.getdbAuthService().getNickname(parts[1], parts[2]);
                if (!nick.isEmpty()) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMsg("/authok " + nick);
                        name = nick;
                        myServer.sentMsgToClient(" зашел в чат", name);
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMsg("Учетная запись уже используется");
                    }
                } else {
                    sendMsg("Неверные логин/пароль");
                }
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() throws IOException {
        while (true) {
            String msgFromClient = dis.readUTF();
            if (msgFromClient.startsWith("/")) {

                if (msgFromClient.startsWith("/ou")) {
                    myServer.sendOnlineClientList(this);
                    continue;
                }
                if (msgFromClient.startsWith("/pm")) {
                    String[] parts = msgFromClient.trim().split(" ", 3);
                    myServer.sentMsgToClient(this, parts[1], parts[2]);
                    continue;
                }
                if (msgFromClient.equals("/q")) {
                    sendMsg(msgFromClient);
                    return;
                }
            }
            myServer.sentMsgToClient(msgFromClient, name);
            System.out.println("Сообщение от " + name + ": " + msgFromClient);
        }
    }

    public String getName() {
        return name;
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.sentMsgToClient(" вышел из чата", name);
        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}

