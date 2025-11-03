package lotto.domain;

import lotto.validator.MoneyValidator;

public record Money(int amount) {
    private static final int LOTTO_PRICE = 1000;

    public Money {
        MoneyValidator.validatePositive(amount);
        MoneyValidator.validateUnit(amount);
    }

    public int getLottoCount() {
        return amount / LOTTO_PRICE;
    }
}
