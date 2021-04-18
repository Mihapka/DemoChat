package Lesson_1.Task_3;

/*
    3. Большая задача:
        a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
        b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта,
    поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
        c. Для хранения фруктов внутри коробки можете использовать ArrayList;
        d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и
    вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
        e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку
        с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в
    противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
        f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
    (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в
    текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
        g. Не забываем про метод добавления фрукта в коробку.
*/

import Lesson_1.Task_3.classes.Apple;
import Lesson_1.Task_3.classes.MyBox;
import Lesson_1.Task_3.classes.Orange;

public class Task_3 {

    public static void main(String[] args) {

        MyBox<Apple> myAppleBox = new MyBox();
        MyBox<Apple> myAppleBox2 = new MyBox();

        MyBox<Orange> myOrangeBox = new MyBox();
        MyBox<Orange> myOrangeBox2 = new MyBox();

        Apple apple0 = new Apple();
        Apple apple1 = new Apple();
        Apple apple2 = new Apple();

        Orange orange0 = new Orange();
        Orange orange1 = new Orange();
        Orange orange2 = new Orange();
        Orange orange3 = new Orange();

        myAppleBox.putOnBox(apple0, apple1, apple2);
        myAppleBox2.putOnBox(apple0, apple1);
        System.out.println("myAppleBox weight: " + myAppleBox.getWeight());
        System.out.println("myAppleBox2 weight: " + myAppleBox2.getWeight());

        myAppleBox2.putToOtherBox(myAppleBox);
        System.out.println("myAppleBox2 weight: " + myAppleBox2.getWeight());
        System.out.println("myAppleBox weight: " + myAppleBox.getWeight());

        myOrangeBox.putOnBox(orange0, orange1, orange2, orange3);
        myOrangeBox2.putOnBox(orange0, orange1);
        System.out.println("myOrangeBox weight: " + myOrangeBox.getWeight());
        System.out.println("myOrangeBox2 weight: " + myOrangeBox2.getWeight());

        myOrangeBox2.putToOtherBox(myOrangeBox);
        System.out.println("myOrangeBox2 weight: " + myOrangeBox2.getWeight());
        System.out.println("myOrangeBox weight: " + myOrangeBox.getWeight());
    }
}
