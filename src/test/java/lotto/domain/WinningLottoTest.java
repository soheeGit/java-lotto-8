package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    @Test
    @DisplayName("당첨 번호와 보너스 번호로 WinningLotto를 생성한다")
    void createWinningLotto() {
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        WinningLotto winningLotto = new WinningLotto(winningNumbers, bonusNumber);

        assertThat(winningLotto).isNotNull();
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다")
    void createWinningLottoWithDuplicateBonus() {
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 6;  // 당첨 번호에 포함됨

        assertThatThrownBy(() -> new WinningLotto(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("중복");
    }

    @ParameterizedTest
    @CsvSource({"0", "-1", "46", "100"})
    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다")
    void createWinningLottoWithInvalidBonusRange(int bonusNumber) {
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> new WinningLotto(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1부터 45");
    }

    @Test
    @DisplayName("6개 일치하면 1등이다")
    void matchSixNumbers() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        Rank rank = winningLotto.match(lotto);

        assertThat(rank).isEqualTo(Rank.FIRST);
    }

    @Test
    @DisplayName("5개 일치하고 보너스가 일치하면 2등이다")
    void matchFiveNumbersWithBonus() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));  // 7이 보너스

        Rank rank = winningLotto.match(lotto);

        assertThat(rank).isEqualTo(Rank.SECOND);
    }

    @Test
    @DisplayName("5개 일치하고 보너스가 불일치하면 3등이다")
    void matchFiveNumbersWithoutBonus() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 8));  // 보너스 불일치

        Rank rank = winningLotto.match(lotto);

        assertThat(rank).isEqualTo(Rank.THIRD);
    }

    @Test
    @DisplayName("4개 일치하면 4등이다")
    void matchFourNumbers() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 10, 11));

        Rank rank = winningLotto.match(lotto);

        assertThat(rank).isEqualTo(Rank.FOURTH);
    }

    @Test
    @DisplayName("3개 일치하면 5등이다")
    void matchThreeNumbers() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        Lotto lotto = new Lotto(List.of(1, 2, 3, 10, 11, 12));

        Rank rank = winningLotto.match(lotto);

        assertThat(rank).isEqualTo(Rank.FIFTH);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 11, 12, 13, 14, 15",  // 0개 일치
            "1, 10, 11, 12, 13, 14",   // 1개 일치
            "1, 2, 10, 11, 12, 13"     // 2개 일치
    })
    @DisplayName("3개 미만 일치하면 낙첨이다")
    void matchLessThanThree(int n1, int n2, int n3, int n4, int n5, int n6) {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        Lotto lotto = new Lotto(List.of(n1, n2, n3, n4, n5, n6));

        Rank rank = winningLotto.match(lotto);

        assertThat(rank).isEqualTo(Rank.MISS);
    }

    @Test
    @DisplayName("4개 일치하고 보너스가 일치해도 4등이다")
    void matchFourNumbersIgnoresBonus() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 7, 10));  // 4개 + 보너스

        Rank rank = winningLotto.match(lotto);

        assertThat(rank).isEqualTo(Rank.FOURTH);  // 보너스는 5개 일치할 때만 영향
    }

    @Test
    @DisplayName("당첨 번호를 반환한다")
    void getWinningNumbers() {
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        WinningLotto winningLotto = new WinningLotto(winningNumbers, 7);

        Lotto result = winningLotto.winningNumbers();

        assertThat(result).isEqualTo(winningNumbers);
    }

    @Test
    @DisplayName("보너스 번호를 반환한다")
    void getBonusNumber() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );

        int bonusNumber = winningLotto.bonusNumber();

        assertThat(bonusNumber).isEqualTo(7);
    }
}