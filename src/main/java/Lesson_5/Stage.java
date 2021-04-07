package Lesson_5;

public abstract class Stage {

    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

// stage - текущий этап, allStages - общее количество этапов
    public abstract void go(Car c, int stage, int allStages);
//  проверям есть ли победитель после каждого этапа т.к. трасса может быть перестроенна
    public void checkWinner(Car car, int stage, int stageCount) {
//        если текущий этап == количеству этапов(последний), а winner == false
//        (нет победителя), то текущий поток - победитель, меняем значение winner на true
//        т.к. winner volatile, то первый поток его меняет, пока остальные ждут, а после изменения
//        условие ифа не выполняется
        if (stage == stageCount && StartRace.winner) {
            StartRace.winner = false;
            System.out.println("\n" + "У нас есть победитель: " + car.getName() + "\n");
        }
    }
}
