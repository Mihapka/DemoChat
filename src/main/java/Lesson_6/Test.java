package Lesson_6;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class Test {

    private int[] baseArray;
    private int[] currentArray;
    private boolean expectedResult;

    public Test(int[] baseArray, int[] currentArray, boolean expectedResult) {

        this.baseArray = baseArray;
        this.currentArray = currentArray;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{5, 6, 7}, true},
                {new int[]{1, 2, 3, 4, 5}, new int[]{5}, true},
                {new int[]{0, 0, 2, 3, 3, 5, 6, 7}, new int[]{}, false},
                {new int[]{4, 4, 4, 1}, new int[]{1}, true}
        });
    }

    @org.junit.Test
    public void checkOneOrFourTest() {
        Assert.assertEquals(expectedResult, TaskClass.checkOneOrFour(baseArray));
    }

    @org.junit.Test
    public void findElementsAfterNum() {
        Assert.assertArrayEquals(currentArray, TaskClass.findElementsAfterNum(baseArray));

    }

    @org.junit.Test(expected = RuntimeException.class)
    public void findElementsAfterNumTestException() {
        TaskClass.findElementsAfterNum(baseArray);
    }
}
