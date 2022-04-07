package Chat.clientside;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class EchoClient extends JFrame {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8181;

    private JTextField msgInputField;
    private JTextArea chatArea;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private boolean isAuthorised;

    public boolean isAuthorised() {
        return isAuthorised;
    }

    public EchoClient() {

        try {
            starConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EchoClient::new);

    }

    public void setAuthorised(boolean authorised) {
        isAuthorised = authorised;
    }

    public void starConnection() throws IOException {

        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        setAuthorised(false);
        openConnection();
    }

    public void openConnection() {

        Thread ConnectionThread = new Thread(() -> {

            try {
                while (true) {
                    String serverMsg = dis.readUTF();
                    if (serverMsg.startsWith("/authok")) {
                        setAuthorised(true);
                        chatArea.append("Вы авторизовались" + "\n");
                        break;
                    }
                    chatArea.append(serverMsg + "\n");
                }
                while (true) {
                    String serverMsg = dis.readUTF();
                    if (serverMsg.equals("/q")) {
                        break;
                    }
                    chatArea.append(serverMsg + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeConnection();
        });
        ConnectionThread.setDaemon(true);
        ConnectionThread.start();

        Thread checkConnectionThread = new Thread(() -> {

            try {
                Thread.sleep(120000);         // 120 секунды в милисекундах
                if (!isAuthorised) {
                    closeConnection();
                    chatArea.append("Вы не авторизовались," + "\n"
                            + "Соединение будет разорванно." + "\n"
                            + "Закройте окно.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        checkConnectionThread.setDaemon(true);
        checkConnectionThread.start();
    }

    public void prepareGUI() {

        setBounds(200, 300, 300, 300);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton sendButton = new JButton("Отправить");
        bottomPanel.add(sendButton, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);

        sendButton.addActionListener(e -> {
            sendMsgToServer();
        });

        msgInputField.addActionListener(e -> {
            sendMsgToServer();
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    dos.writeUTF("/q");
                    closeConnection();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    public void sendMsgToServer() {

        if (!msgInputField.getText().trim().isEmpty()) {
            try {
                String msgToServer = msgInputField.getText();
                dos.writeUTF(msgToServer);
                if (!msgToServer.equals("/q")) {
                    msgInputField.setText("");
                    return;
                }
                msgInputField.setText("");
                msgInputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
                msgInputField.setText("");
            }
        }
    }

    public void closeConnection() {

        try {
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }
}