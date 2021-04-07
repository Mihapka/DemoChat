package Lesson_5;

public class Road extends Stage {

    public Road(int length) {

        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car car, int stage, int allStages) {

        try {
            System.out.println(car.getName() + " начал этап: " + description);
            Thread.sleep(length / car.getSpeed() * 1000);
            System.out.println(car.getName() + " закончил этап: " + description);
            checkWinner(car, stage, allStages);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
