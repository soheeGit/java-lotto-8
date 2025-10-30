package lotto.util;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LottoGeneratorTest {
    @Test
    @DisplayName("로또 번호를 생성한다")
    void generateLotto() {
        Lotto lotto = LottoGenerator.generate();
        assertThat(lotto.getNumbers()).hasSize(6);
    }

    @Test
    @DisplayName("여러 개의 로또를 생성한다")
    void generateMultipleLottos() {
        List<Lotto> lottos = LottoGenerator.generateMultiple(5);
        assertThat(lottos).hasSize(5);
    }

    @Test
    @DisplayName("생성된 로또는 1~45 범위의 숫자를 포함한다")
    void generateLottoWithValidRange() {
        Lotto lotto = LottoGenerator.generate();
        List<Integer> numbers = lotto.getNumbers();

        assertThat(numbers)
                .allMatch(num -> num >= 1 && num <= 45);
    }
}
