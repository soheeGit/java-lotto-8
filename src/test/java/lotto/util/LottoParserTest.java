package lotto.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class LottoParserTest {
    @Test
    @DisplayName("문자열을 숫자로 변환한다")
    void parseStringToInt() {
        int result = LottoParser.from("5000");
        assertThat(result).isEqualTo(5000);
    }

    @Test
    @DisplayName("숫자가 아닌 문자열은 예외를 발생시킨다")
    void parseInvalidString() {
        assertThatThrownBy(() -> LottoParser.from("abc"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("쉼표로 구분된 당첨 번호를 파싱한다")
    void parseWinningNumbers() {
        List<Integer> numbers = LottoParser.parseWinningNumbers("1,2,3,4,5,6");

        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("공백이 포함된 당첨 번호를 파싱한다")
    void parseWinningNumbersWithSpaces() {
        List<Integer> numbers = LottoParser.parseWinningNumbers("1, 2, 3, 4, 5, 6");

        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("순서와 상관없이 당첨 번호를 파싱한다")
    void parseWinningNumbersInAnyOrder() {
        List<Integer> numbers = LottoParser.parseWinningNumbers("6,3,1,5,2,4");

        assertThat(numbers).containsExactly(6, 3, 1, 5, 2, 4);
    }

    @Test
    @DisplayName("당첨 번호가 6개가 아니면 예외가 발생한다")
    void parseWinningNumbersWithInvalidSize() {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers("1,2,3,4,5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,a", "1,2,3,4,5,!", "a,b,c,d,e,f"})
    @DisplayName("숫자가 아닌 값이 포함되면 예외가 발생한다")
    void parseWinningNumbersWithNonNumeric(String input) {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("빈 문자열이면 예외가 발생한다")
    void parseWinningNumbersWithEmptyString() {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("null 입력시 예외가 발생한다")
    void parseWinningNumbersWithNull() {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,1,2,3,4,5", "1,2,3,4,5,46", "-1,1,2,3,4,5", "1,2,3,4,5,100"})
    @DisplayName("1~45 범위를 벗어난 번호가 있으면 예외가 발생한다")
    void parseWinningNumbersWithInvalidRange(String input) {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("1부터 45");
    }

    @Test
    @DisplayName("중복된 당첨 번호가 있으면 예외가 발생한다")
    void parseWinningNumbersWithDuplicates() {
        assertThatThrownBy(() -> LottoParser.parseWinningNumbers("1,2,3,4,5,5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("중복");
    }

    @Test
    @DisplayName("보너스 번호를 파싱한다")
    void parseBonusNumber() {
        int bonus = LottoParser.parseBonusNumber("7");

        assertThat(bonus).isEqualTo(7);
    }

    @Test
    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다")
    void parseBonusNumberWithNonNumeric() {
        assertThatThrownBy(() -> LottoParser.parseBonusNumber("a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "46", "-1", "100"})
    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다")
    void parseBonusNumberWithInvalidRange(String input) {
        assertThatThrownBy(() -> LottoParser.parseBonusNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("빈 문자열이나 공백은 예외가 발생한다")
    void parseEmptyOrWhitespace(String input) {
        assertThatThrownBy(() -> LottoParser.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("null 입력시 예외가 발생한다")
    void parseNull() {
        assertThatThrownBy(() -> LottoParser.from(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("음수를 파싱한다")
    void parseNegativeNumber() {
        int result = LottoParser.from("-1000");

        assertThat(result).isEqualTo(-1000);
    }
}
