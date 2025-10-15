package calculator;

import java.util.List;

public class Calculator {

    public static int sum(List<Integer> operands) {
        int sum = 0;

        for (Integer operand : operands) {
            sum += operand;
        }

        return sum;
    }
}
