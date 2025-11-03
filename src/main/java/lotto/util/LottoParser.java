package lotto.util;

import lotto.exception.ErrorMessage;
import lotto.validator.LottoValidator;

import java.util.Arrays;
import java.util.List;

public class LottoParser {
    private static final String DELIMITER = ",";

    public static int from(String input) {
        return parseToInt(input);
    }

    public static List<Integer> parseWinningNumbers(String input) {
        validateNotEmpty(input);

        String[] tokens = input.split(DELIMITER);
        List<Integer> numbers = Arrays.stream(tokens)
                .map(String::trim)
                .map(LottoParser::parseToInt)
                .toList();

        LottoValidator.validateSize(numbers);
        LottoValidator.validateRange(numbers);
        LottoValidator.validateDuplicate(numbers);

        return numbers;
    }

    public static int parseBonusNumber(String input) {
        validateNotEmpty(input);
        int number = parseToInt(input.trim());
        LottoValidator.validateNumberRange(number);
        return number;
    }

    private static int parseToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT);
        }
    }

    private static void validateNotEmpty(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_INPUT);
        }
    }
}
