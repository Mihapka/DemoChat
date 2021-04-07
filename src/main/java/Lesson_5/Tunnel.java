package Lesson_5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

//    ограничиваем доступ к тоннелю половиной участников и используем параметр true
//    чтобы потоки получали доступ к тоннелю в том порядке, в котором встали в очередь
    private static final Semaphore semaphore = new Semaphore(StartRace.CARS_COUNT / 2, true);

    public Tunnel() {

        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car car, int stage, int allStages) {

        try {
            try {
                System.out.println(car.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire();
                System.out.println(car.getName() + " начал этап: " + description);
                Thread.sleep(length / car.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println(car.getName() + " закончил этап: " + description);
                checkWinner(car, stage, allStages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
