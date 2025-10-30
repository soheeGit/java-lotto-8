package lotto.validator;

import lotto.exception.ErrorMessage;

public class MoneyValidator {
    private static final int LOTTO_PRICE = 1000;

    public static void validatePositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_AMOUNT_POSITIVE);
        }
    }

    public static void validateUnit(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_AMOUNT);
        }
    }
}
