package Lesson_4;

/*
    1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
        (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
    2. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.
*/

public class ThredTask {
    static String a = "A";

    public static void main(String[] args) {
        Object monitor = new Object();

        class MyClass implements Runnable {
            private String currentWord;
            private String nextWodr;

            public MyClass(String cгrrentWord, String nextWodr) {
                this.currentWord = cгrrentWord;
                this.nextWodr = nextWodr;
            }

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronized (monitor) {
                        try {
                            while (!a.equals(currentWord))
                                monitor.wait();
                            System.out.println(currentWord);
                            if (currentWord.equals("C")) {
                                System.out.println("end of turn #" + " " + (i + 1));
                            }
                            a = nextWodr;
                            monitor.notifyAll();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }

        new Thread(new MyClass("A", "B")).start();
        new Thread(new MyClass("B", "C")).start();
        new Thread(new MyClass("C", "A")).start();
    }
}
