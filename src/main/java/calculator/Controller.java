package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.List;
import java.util.Set;

public class Controller {

    public static void run() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");

        String input = Console.readLine();

        Set<Character> separators = Parser.parseSeparator(input);
        List<Integer> operands = Parser.parseOperands(input, separators);

        int sum = Calculator.sum(operands);

        System.out.println("결과 : " + sum);
    }

}
