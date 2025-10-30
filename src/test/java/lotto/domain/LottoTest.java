package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    @Test
    @DisplayName("로또 번호가 6개일 때 정상 생성된다")
    void createLottoWithSixNumbers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        Lotto lotto = new Lotto(numbers);
        assertThat(lotto).isNotNull();
    }

    @Test
    @DisplayName("로또 번호가 6개가 아니면 예외가 발생한다")
    void createLottoWithInvalidSize() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRangeNumbers")
    @DisplayName("로또 번호가 1~45 범위를 벗어나면 예외가 발생한다")
    void createLottoWithInvalidRange(List<Integer> numbers) {
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    static Stream<Arguments> provideInvalidRangeNumbers() {
        return Stream.of(
                Arguments.of(List.of(0, 1, 2, 3, 4, 5)),      // 0 포함
                Arguments.of(List.of(1, 2, 3, 4, 5, 46)),     // 46 포함
                Arguments.of(List.of(-1, 1, 2, 3, 4, 5)),     // 음수 포함
                Arguments.of(List.of(1, 2, 3, 4, 5, 100))     // 100 포함
        );
    }

    @Test
    @DisplayName("로또 번호에 중복이 있으면 예외가 발생한다")
    void createLottoWithDuplicateNumbers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 5);

        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("당첨 번호와 일치하는 개수를 반환한다")
    void countMatchingNumbers() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        List<Integer> winningNumbers = List.of(1, 2, 3, 7, 8, 9);

        int count = lotto.countMatches(winningNumbers);

        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("보너스 번호 포함 여부를 확인한다")
    void containsBonusNumber() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.contains(7)).isFalse();
        assertThat(lotto.contains(1)).isTrue();
    }

    @Test
    @DisplayName("로또 번호를 정렬하여 반환한다")
    void getSortedNumbers() {
        Lotto lotto = new Lotto(List.of(6, 3, 1, 5, 2, 4));

        List<Integer> sorted = lotto.getNumbers();

        assertThat(sorted).containsExactly(1, 2, 3, 4, 5, 6);
    }
}