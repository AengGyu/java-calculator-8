package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.List;
import java.util.Set;

public class Controller {

    public static void run() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");

        String input = Console.readLine();

        Set<Character> separators = getSeparators(input);
        List<Integer> operands = getOperands(input, separators);

        int sum = Calculator.sum(operands);

        System.out.println("결과 : " + sum);
    }

    private static Set<Character> getSeparators(String input) {
        Set<Character> separators = Parser.parseSeparator(input);

        if (separators == null) {
            return Set.of(',', ':');
        }

        separators.add(',');
        separators.add(':');
        return separators;
    }

    private static List<Integer> getOperands(String input, Set<Character> separators) {
        return Parser.parseOperands(input, separators);
    }
}
