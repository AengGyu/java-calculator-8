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
     * 문자열을 입력 받아서 //과 \n 으로 끝나는 경우 그 사이의 문자들을 하나씩 Set에 저장
     * 구분자 관련 오류 체크는 한 번에 메서드로 하는 게 나을 수도?
     */
    public static Set<Character> parseSeparator(String input) {

        Set<Character> separators = new HashSet<>();

        if (input.startsWith("//")) {
            int endSeparator = input.indexOf("\\n");

            if (endSeparator == 2) {
                throw new IllegalArgumentException("커스텀 구분자를 입력해주세요.");
            }

            String separator = input.substring(2, endSeparator);

            for (int i = 0; i < separator.length(); i++) {
                if (separator.charAt(i) >= '0' && separator.charAt(i) <= '9') {
                    throw new IllegalArgumentException("숫자는 구분자로 사용할 수 없습니다.");
                }
                separators.add(separator.charAt(i));
            }

            return separators;
        }

        return null;
    }

    /**
     * 구분자를 제외한 피연산자를 꺼내서 리스트에 저장후 리턴
     */
    public static List<Integer> parseOperands(String input, Set<Character> separators) {

        String beforeParsing;
        List<Integer> operands = new ArrayList<>();

        if (separators.size() == 2) {
            beforeParsing = input;
        } else {
            beforeParsing = input.substring(2 + separators.size());
        }

        int tmp = 0;
        for (int i = 0; i < beforeParsing.length(); i++) {
            char operand = beforeParsing.charAt(i);

            if (operand >= '0' && operand <= '9') {
                tmp = tmp * 10 + operand - '0';
            }

            else {
                if (!separators.contains(operand)) {
                    throw new IllegalArgumentException("',', ':' 이 외의 구분자는 문자열 맨 앞에 //와 \\n 사이에 입력 해주세요.");
                }

                operands.add(tmp);
                tmp = 0;
            }
        }

        operands.add(tmp);

        return operands;
    }
}
