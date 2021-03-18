package Lesson_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Task_2 {

    public static void main(String[] args) {

        String[] arr = {"a","s","d",};
        arrToArrayList(arr);
    }
    public static void arrToArrayList(String[]arr){

        System.out.println(Arrays.toString(arr));
        ArrayList<String> listOfStrings = new ArrayList<String>();
        Collections.addAll(listOfStrings, arr);
        for (String a:listOfStrings ) {
            System.out.println(a);
        }
    }
}
