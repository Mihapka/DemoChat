package Lesson_1.Task_3.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBox<T extends Fruits> {

    private final List<T> fruits;

    public MyBox() {
        fruits = new ArrayList<>();
    }

    public double getWeight() {

        double sum = 0.0;
        for (Fruits f : fruits) {
            sum += f.getWeight();
        }
        return sum;
    }

    public void putOnBox(T... fruit) {
        fruits.addAll(Arrays.asList(fruit));
    }

    public void putToOtherBox(MyBox<T> box) {

        fruits.addAll(box.fruits);
        box.fruits.clear();
    }

    public <E extends Fruits> boolean compare(MyBox<E> box) {
        return getWeight() == box.getWeight();
    }

}

