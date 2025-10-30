package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {
    @Test
    @DisplayName("1000원 단위로 Money 객체가 정상 생성된다")
    void createMoneyWithValidAmount() {
        Money money = new Money(5000);
        assertThat(money).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1500, 2300, 999, 10001})
    @DisplayName("1000원 단위가 아니면 예외가 발생한다")
    void createMoneyWithInvalidAmount(int amount) {
        assertThatThrownBy(() -> new Money(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1000, -5000})
    @DisplayName("0원 이하 금액이면 예외가 발생한다")
    void createMoneyWithNonPositiveAmount(int amount) {
        assertThatThrownBy(() -> new Money(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("구입 금액으로 로또 구매 개수를 계산한다")
    void calculateLottoCount() {
         Money money = new Money(5000);

         int count = money.getLottoCount();

         assertThat(count).isEqualTo(5);
    }

    @Test
    @DisplayName("8000원으로 8개 로또를 구매할 수 있다")
    void calculateLottoCountWith8000() {
        Money money = new Money(8000);

        int count = money.getLottoCount();

        assertThat(count).isEqualTo(8);
    }
}