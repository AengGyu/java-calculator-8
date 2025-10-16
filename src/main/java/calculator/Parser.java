package calculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * parseSeparator : 기본 구분자를 제외한 커스텀 구분자를 파싱해서 리스트로 반환
 * parseOperands : 피연산자를 파싱해서 리스트로 반환
 */
public class Parser {

    /**
     * 문자열을 입력 받아서 커스텀 구분자가 있는 경우 커스텀 구분자와 기본 구분자를 포함하여 Set에 저장 후 반환
     * 커스텀 구분자가 없는 경우 기본 구분자만 포함한 Set 반환
     */
    public static Set<Character> parseSeparator(String input) {

        if (hasCustomSeparator(input)) {
            return getCustomSeparators(input);
        }

        return Set.of(',', ':');
    }

    /**
     * 입력한 계산식에서 구분자를 제외하고 덧셈할 피연산자만 List에 저장 후 반환
     */
    public static List<Integer> parseOperands(String input, Set<Character> separators) {

        String expression = getExpression(input, separators);
        return getOperands(separators, expression);
    }

    /**
     * //로 시작하는지 확인하는 메서드
     */
    private static boolean hasCustomSeparator(String input) {
        return input.startsWith("//");
    }

    /**
     * 입력한 문자가 숫자인지 확인하는 메서드
     */
    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 커스텀 구분자의 끝의 인덱스를 반환하는 메서드
     */
    private static int getIdxOfCustomSeparatorEnd(String input) {
        return input.indexOf("\\n");
    }

    /**
     * // 와 \n 사이의 입력된 커스텀 구분자 문자열을 반환하는 메서드
     * 메서드 이름 뭘로 하지 ??????
     */
    private static String getString(String input) {
        int idx = getIdxOfCustomSeparatorEnd(input);
        checkCustomSeparator(idx);

        return input.substring(2, idx);
    }

    /**
     * 커스텀 구분자를 Set에 저장 후 반환하는 메서드
     * 숫자는 커스텀 구분자에 포함될 수 없음
     */
    private static Set<Character> getCustomSeparators(String input) {
        Set<Character> separators = new HashSet<>();

        String customSeparators = getString(input);

        for (int i = 0; i < customSeparators.length(); i++) {
            if (isNumber(customSeparators.charAt(i))) {
                throw new IllegalArgumentException("숫자는 구분자로 사용할 수 없습니다.");
            }
            separators.add(customSeparators.charAt(i));
        }

        separators.add(',');
        separators.add(':');

        return separators;
    }

    // TODO: 구분자가 연속해서 오는 경우 예외처리
    // TODO: '-' 는 어떻게 처리할 것인지

    /**
     * 커스텀 구분자가 있으면 떼어낸 후 피연산자를 하나씩 리스트에 저장 후 리턴
     * 구분자에 포함되지 않는 문자가 피연산자 사이에 있는 경우 예외처리
     */
    private static List<Integer> getOperands(Set<Character> separators, String expression) {

        int num = 0;
        List<Integer> operands = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            char operand = expression.charAt(i);

            if (isNumber(operand)) {
                num = num * 10 + operand - '0';
            } else {
                if (!separators.contains(operand)) {
                    checkExpression(operand);
                }

                operands.add(num);
                num = 0;
            }
        }

        operands.add(num);

        return operands;
    }

    /**
     * 계산식을 반환하는 메서드
     * 커스텀 구분자가 없다면 input을 그대로 반환
     * 커스텀 구분자가 있으면 커스텀 구분자가 입력된 부분을 제외한 계산식만 반환
     */
    private static String getExpression(String input, Set<Character> separators) {

        if (separators.size() == 2) {
            return input;
        }

        return input.substring(2 + separators.size());
    }

    /**
     * 계산식에 음수(-) 또는 구분자로 지정되지 않은 문자가 들어있는 경우 예외처리
     */
    private static void checkExpression(char operand) {
        if (operand == '-') {
            throw new IllegalArgumentException("음수는 피연산자로 사용할 수 없습니다.");
        }

        throw new IllegalArgumentException("',', ':' 이 외의 구분자는 문자열 맨 앞에 //와 \\n 사이에 입력 해주세요.");
    }

    /**
     * // 와 \n 사이에 아무 문자가 없거나, \n이 없는 경우 예외처리
     */
    private static void checkCustomSeparator(int idx) {
        if (idx == 2 || idx == -1) {
            throw new IllegalArgumentException("커스텀 구분자를 // 와 \\n 사이에 입력해주세요.");
        }
    }
}
