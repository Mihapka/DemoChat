package Lesson_7;

import Lesson_7.Interface.AfterSuite;
import Lesson_7.Interface.BeforeSuite;
import Lesson_7.Interface.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

/*
            1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы
        с наборами методов с аннотациями @Test. Для этого у него должен быть статический метод start(),
        которому в качестве параметра передается или объект типа Class, или имя класса. Из «класса-теста»
        вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется, далее запущены
        методы с аннотациями @Test, а по завершению всех тестов – метод с аннотацией @AfterSuite.
        К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии с
        которыми будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет
        значения. Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном
        экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».

        Это домашнее задание никак не связано с темой тестирования через JUnit и использованием этой
        библиотеки, то есть проект пишется с нуля.
 */

public class MainClass {

    public static void main(String[] args) throws Exception {

        Class c = TestsClass.class;
        Object testObj = c.newInstance();
        Method[] methods = c.getDeclaredMethods();
        ArrayList<Method> al = new ArrayList<>();
        Method beforeMethod = null;
        Method afterMethod = null;
        for (Method o : c.getDeclaredMethods()) {

            if (o.isAnnotationPresent(Test.class)) {
                al.add(o);
            }
            if (o.isAnnotationPresent(BeforeSuite.class)) {

                if (beforeMethod == null) {
                    beforeMethod = o;
                } else throw new RuntimeException("Больше одного метода с аннотацией BeforeSuite");
            }
            if (o.isAnnotationPresent(AfterSuite.class)) {

                if (afterMethod == null) {
                    afterMethod = o;
                } else throw new RuntimeException("Больше одного метода с аннотацией AfterSuite");
            }
            al.sort(new Comparator<Method>() {
                @Override
                public int compare(Method o1, Method o2) {
                    return o2.getAnnotation(Test.class).priority() - o1.getAnnotation(Test.class).priority();
                }
            });
        }

        if (beforeMethod != null) {
            beforeMethod.invoke(testObj, null);
        }
        for (Method o : al) {

            o.invoke(testObj, null);
            if (afterMethod != null) {
                afterMethod.invoke(testObj, null);
            }
        }
    }
}