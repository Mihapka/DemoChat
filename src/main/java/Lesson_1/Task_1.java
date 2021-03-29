package Lesson_1;

/*
1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
*/

import java.util.Arrays;

public class Task_1 {
    public static void main(String[] args) {

        String[] listStr = new String[]{"q", "w", "e", "r", "t", "y"};
        Integer[] listInt = {1, 2, 3, 4, 5, 6};
        swapElements(listStr, 2, 3);
        swapElements(listInt, 2, 3);

    }

    public static <T> void swapElements(Object[] arr, int a, int b) {

        System.out.println(Arrays.toString(arr));
        Object temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        System.out.println(Arrays.toString(arr) + "\n");
    }
}
