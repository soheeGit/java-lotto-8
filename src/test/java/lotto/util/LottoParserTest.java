package lotto.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
                .isInstanceOf(NumberFormatException.class);
    }
}
