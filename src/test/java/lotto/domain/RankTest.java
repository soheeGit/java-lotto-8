package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @Test
    @DisplayName("6개 일치하면 1등이다")
    void matchSixNumbers() {
        Rank rank = Rank.from(6, false);

        assertThat(rank).isEqualTo(Rank.FIRST);
    }

    @Test
    @DisplayName("5개 일치하고 보너스가 일치하면 2등이다")
    void matchFiveNumbersWithBonus() {
        Rank rank = Rank.from(5, true);

        assertThat(rank).isEqualTo(Rank.SECOND);
    }

    @Test
    @DisplayName("5개 일치하고 보너스가 불일치하면 3등이다")
    void matchFiveNumbersWithoutBonus() {
        Rank rank = Rank.from(5, false);

        assertThat(rank).isEqualTo(Rank.THIRD);
    }

    @Test
    @DisplayName("4개 일치하면 4등이다")
    void matchFourNumbers() {
        Rank rank = Rank.from(4, false);

        assertThat(rank).isEqualTo(Rank.FOURTH);
    }

    @Test
    @DisplayName("3개 일치하면 5등이다")
    void matchThreeNumbers() {
        Rank rank = Rank.from(3, false);

        assertThat(rank).isEqualTo(Rank.FIFTH);
    }

    @ParameterizedTest
    @CsvSource({"0, false", "1, false", "2, false", "0, true", "1, true", "2, true"})
    @DisplayName("3개 미만 일치하면 낙첨이다")
    void matchLessThanThree(int matchCount, boolean bonusMatch) {
        Rank rank = Rank.from(matchCount, bonusMatch);

        assertThat(rank).isEqualTo(Rank.MISS);
    }

    @Test
    @DisplayName("6개 일치시 보너스는 영향을 주지 않는다")
    void matchSixNumbersIgnoresBonus() {
        Rank rankWithBonus = Rank.from(6, true);
        Rank rankWithoutBonus = Rank.from(6, false);

        assertThat(rankWithBonus).isEqualTo(Rank.FIRST);
        assertThat(rankWithoutBonus).isEqualTo(Rank.FIRST);
        assertThat(rankWithBonus).isEqualTo(rankWithoutBonus);
    }

    @Test
    @DisplayName("4개 이하 일치시 보너스는 영향을 주지 않는다")
    void matchFourOrLessIgnoresBonus() {
        Rank fourWithBonus = Rank.from(4, true);
        Rank fourWithoutBonus = Rank.from(4, false);

        assertThat(fourWithBonus).isEqualTo(Rank.FOURTH);
        assertThat(fourWithoutBonus).isEqualTo(Rank.FOURTH);
    }

    @ParameterizedTest
    @CsvSource({
            "FIRST, 2000000000",
            "SECOND, 30000000",
            "THIRD, 1500000",
            "FOURTH, 50000",
            "FIFTH, 5000"
    })
    @DisplayName("각 등수의 상금을 반환한다")
    void getPrizeByRank(Rank rank, int expectedPrize) {
        int prize = rank.getPrize();

        assertThat(prize).isEqualTo(expectedPrize);
    }

    @Test
    @DisplayName("낙첨의 상금은 0원이다")
    void getMissPrize() {
        int prize = Rank.MISS.getPrize();

        assertThat(prize).isEqualTo(0);
    }

    @Test
    @DisplayName("1등 상금은 20억원이다")
    void getFirstPrize() {
        int prize = Rank.FIRST.getPrize();

        assertThat(prize).isEqualTo(2_000_000_000);
    }

    @Test
    @DisplayName("2등 상금은 3천만원이다")
    void getSecondPrize() {
        int prize = Rank.SECOND.getPrize();

        assertThat(prize).isEqualTo(30_000_000);
    }

    @Test
    @DisplayName("3등 상금은 150만원이다")
    void getThirdPrize() {
        int prize = Rank.THIRD.getPrize();

        assertThat(prize).isEqualTo(1_500_000);
    }

    @Test
    @DisplayName("4등 상금은 5만원이다")
    void getFourthPrize() {
        int prize = Rank.FOURTH.getPrize();

        assertThat(prize).isEqualTo(50_000);
    }

    @Test
    @DisplayName("5등 상금은 5천원이다")
    void getFifthPrize() {
        int prize = Rank.FIFTH.getPrize();

        assertThat(prize).isEqualTo(5_000);
    }

    @ParameterizedTest
    @CsvSource({
            "3, false, '3개 일치'",
            "4, false, '4개 일치'",
            "5, false, '5개 일치'",
            "5, true, '5개 일치, 보너스 볼 일치'",
            "6, false, '6개 일치'"
    })
    @DisplayName("각 등수의 설명을 반환한다")
    void getDescription(int matchCount, boolean bonusMatch, String expectedDescription) {
        Rank rank = Rank.from(matchCount, bonusMatch);

        String description = rank.getDescription();

        assertThat(description).isEqualTo(expectedDescription);
    }

    @Test
    @DisplayName("낙첨은 당첨 여부가 false이다")
    void missIsNotWinning() {
        boolean isWinning = Rank.MISS.isWinning();

        assertThat(isWinning).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH"})
    @DisplayName("1등부터 5등까지는 당첨이다")
    void rankIsWinning(Rank rank) {
        boolean isWinning = rank.isWinning();

        assertThat(isWinning).isTrue();
    }

    @Test
    @DisplayName("1등부터 5등까지 순서대로 정렬되어 있다")
    void rankOrder() {
        Rank[] ranks = Rank.values();

        assertThat(ranks).containsExactly(
                Rank.MISS,
                Rank.FIFTH,
                Rank.FOURTH,
                Rank.THIRD,
                Rank.SECOND,
                Rank.FIRST
        );
    }

    @Test
    @DisplayName("등수별 일치 개수를 반환한다")
    void getMatchCount() {
        assertThat(Rank.FIRST.getMatchCount()).isEqualTo(6);
        assertThat(Rank.SECOND.getMatchCount()).isEqualTo(5);
        assertThat(Rank.THIRD.getMatchCount()).isEqualTo(5);
        assertThat(Rank.FOURTH.getMatchCount()).isEqualTo(4);
        assertThat(Rank.FIFTH.getMatchCount()).isEqualTo(3);
    }

    @ParameterizedTest
    @CsvSource({
            "7, false",
            "8, false",
            "10, true",
            "-1, false"
    })
    @DisplayName("유효하지 않은 일치 개수는 낙첨으로 처리된다")
    void invalidMatchCountReturnsMiss(int matchCount, boolean bonusMatch) {
        Rank rank = Rank.from(matchCount, bonusMatch);

        assertThat(rank).isEqualTo(Rank.MISS);
    }
}