package Lesson_2.serverside.service;

import java.io.*;
import java.util.ArrayList;

public class ChatLog {

    private final static int MAX_MESSAGES = 100;


    public static void lastMsgLoger(String message, String fileName) {

        File file = new File(fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = message.getBytes();
        try {
            fileOutputStream.write(buffer);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String lastMsgLoader(String logFileName) throws IOException {

        ArrayList<String> chatArrayList = new ArrayList<String>();
        String temp;
        try {
            FileInputStream fileInputStream = fileInputStream = new FileInputStream(logFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((temp = reader.readLine()) != null) {
                chatArrayList.add(temp);
                if (chatArrayList.size() > MAX_MESSAGES)
                    chatArrayList.remove(0);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл истории чата отсутствует");
        }

        String res = "";
        for (String line : chatArrayList) {
            res += line + "\n";
        }
        if (!res.isEmpty()) {
            System.out.println(res);
            return "Лог чата: " + "\n" + res;
        } else {
            return "Лог чата пуст";
        }
    }
}
