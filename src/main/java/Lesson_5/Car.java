package Lesson_5;

import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {

    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }
// если не сделать static, то зависает на старте гонки
    private static final CountDownLatch cdlForPrepareRace = new CountDownLatch(StartRace.CARS_COUNT);
    private final CountDownLatch countDownLatchReady;
    private final CountDownLatch countDownLatchRaceOver;
    private final int speed;
    private Race race;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed,
               CountDownLatch countDownLatchReady,
               CountDownLatch countDownLatchRaceOver) {

        this.countDownLatchReady = countDownLatchReady;
        this.countDownLatchRaceOver = countDownLatchRaceOver;
        this.race = race;
        this.speed = speed;
        this.name = "Участник #" + CARS_COUNT;
        CARS_COUNT++;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            countDownLatchReady.countDown();
            cdlForPrepareRace.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cdlForPrepareRace.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this, i+1, race.getStages().size());
        }

        countDownLatchRaceOver.countDown();
    }
}
