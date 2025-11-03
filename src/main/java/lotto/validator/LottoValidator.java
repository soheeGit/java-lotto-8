package lotto.validator;

import lotto.domain.Lotto;
import lotto.exception.ErrorMessage;

import java.util.HashSet;
import java.util.List;

public class LottoValidator {
    private static final int LOTTO_SIZE = 6;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;

    public static void validateSize(List<Integer> numbers) {
        if(numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_SIZE);
        }
    }

    public static void validateRange(List<Integer> numbers) {
        if (numbers.stream().anyMatch(num -> num < MIN_NUMBER || num > MAX_NUMBER)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_RANGE);
        }
    }

    public static void validateDuplicate(List<Integer> numbers) {
        if (numbers.size() != new HashSet<>(numbers).size()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_DUPLICATE);
        }
    }

    public static void validateNumberRange(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_RANGE);
        }
    }

    public static void validateBonusRange(int bonusNumber) {
        if (bonusNumber < MIN_NUMBER || bonusNumber > MAX_NUMBER) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BONUS_LOTTO_RANGE);
        }
    }

    public static void validateBonusDuplicate(Lotto winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BONUS_LOTTO_DUPLICATE);
        }
    }

    public static void validateNotEmpty(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_INPUT);
        }
    }
}
