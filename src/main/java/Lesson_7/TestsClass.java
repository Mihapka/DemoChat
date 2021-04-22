package Lesson_7;

import Lesson_7.Interface.AfterSuite;
import Lesson_7.Interface.BeforeSuite;
import Lesson_7.Interface.Test;

public class TestsClass {

    @BeforeSuite
    public void init() {

        System.out.println("Старт.");
    }

    @Test(priority = 3)
    public void test1() {

        System.out.println("тест 1.");
    }

    @Test(priority = 7)
    public void test2() {

        System.out.println("Тест 2.");
    }

    @Test(priority = 1)
    public void test3() {

        System.out.println("Тест 3.");
    }

    @Test(priority = 4)
    public void test4() {

        System.out.println("Тест 4.");
    }

    public void method() {

        System.out.println("Метод.");
    }

    @AfterSuite
    public void stop() {

        System.out.println("Стоп.");
    }

}