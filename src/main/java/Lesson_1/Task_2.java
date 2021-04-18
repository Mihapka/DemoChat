package Lesson_1;

/*
        2. Написать метод, который преобразует массив в ArrayList;
*/

import java.util.ArrayList;
import java.util.Arrays;

public class Task_2 {

    public static void main(String[] args) {

        String[] arr = {"a", "s", "d",};
        arrayToArrayList(arr);
    }

    public static void arrayToArrayList(String[] arr) {

        Arrays.stream(arr).forEach(System.out::println);
        System.out.println("");
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            stringArrayList.add(i, arr[i]);
        }
        stringArrayList.forEach(System.out::println);
    }
}