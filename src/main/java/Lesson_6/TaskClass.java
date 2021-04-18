package Lesson_6;

/*
        1. Добавить на серверную сторону чата логирование, с выводом информации о действиях на сервере
    (запущен, произошла ошибка, клиент подключился, клиент прислал сообщение/команду).

        2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
    Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
    идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
    необходимо выбросить RuntimeException. Написать набор тестов для этого метода
    (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].

        3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки
    или единицы, то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Main {

    public static void main(String[] args) {

        int[] baseArray = TaskClass.createArray();
        Arrays.stream(baseArray).forEach(System.out::print);
        System.out.println("\n" + "------------");
        int[] currentArray = TaskClass.findElementsAfterNum(baseArray);
        Arrays.stream(currentArray).forEach(System.out::print);
        System.out.println("\n" + "------------");
        System.out.println(TaskClass.checkOneOrFour(baseArray));
    }
}

public class TaskClass {

    final static int ARRAY_LENGHT = 10;
    final static int magicNumber = 4;

    public static int[] createArray() {

        int[] baseArray = new int[ARRAY_LENGHT];
        for (int i = 0; i < baseArray.length; i++) {
            baseArray[i] = (int) (Math.random() * 10);
        }
        return baseArray;
    }

    public static int[] findElementsAfterNum(int[] arr) {

        if (arr.length != 0) {
            List<Integer> currentArray = Arrays.stream(arr).boxed().collect(Collectors.toList());
            if (!currentArray.contains(magicNumber)) {
                System.out.println("Ни одной четвёрки.");
                throw new RuntimeException();
            } else {
                for (int i = arr.length - 1; i >= 0; i--) {
                    if (arr[i] == magicNumber) {
                        return Arrays.copyOfRange(arr, i + 1, arr.length);
                    }
                }
            }
        }
        return arr;
    }

    public static boolean checkOneOrFour(int[] arr) {

        for (int i : arr) {
            if (i == 1 || i == 4)
                return true;
        }
        return false;
    }
}