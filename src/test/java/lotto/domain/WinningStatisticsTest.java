package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WinningStatisticsTest {

    @Test
    @DisplayName("당첨 통계를 생성한다")
    void createWinningStatistics() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6))  // 1등
        ));

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);

        assertThat(statistics).isNotNull();
    }

    @Test
    @DisplayName("각 등수별 당첨 개수를 조회한다")
    void getRankCounts() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),     // 1등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),     // 2등 (보너스)
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),     // 3등
                new Lotto(List.of(1, 2, 3, 4, 10, 11)),   // 4등
                new Lotto(List.of(1, 2, 3, 10, 11, 12))   // 5등
        ));

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        Map<Rank, Integer> rankCounts = statistics.getRankCounts();

        assertThat(rankCounts.get(Rank.FIRST)).isEqualTo(1);
        assertThat(rankCounts.get(Rank.SECOND)).isEqualTo(1);
        assertThat(rankCounts.get(Rank.THIRD)).isEqualTo(1);
        assertThat(rankCounts.get(Rank.FOURTH)).isEqualTo(1);
        assertThat(rankCounts.get(Rank.FIFTH)).isEqualTo(1);
    }

    @Test
    @DisplayName("낙첨은 통계에 포함하지 않는다")
    void excludeMissFromStatistics() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(10, 11, 12, 13, 14, 15)),  // 낙첨
                new Lotto(List.of(1, 2, 3, 10, 11, 12))      // 5등
        ));

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        Map<Rank, Integer> rankCounts = statistics.getRankCounts();

        assertThat(rankCounts.containsKey(Rank.MISS)).isFalse();
        assertThat(rankCounts.get(Rank.FIFTH)).isEqualTo(1);
    }

    @Test
    @DisplayName("같은 등수가 여러 개 있으면 개수를 합산한다")
    void countMultipleSameRanks() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12)),  // 5등
                new Lotto(List.of(1, 2, 3, 13, 14, 15)),  // 5등
                new Lotto(List.of(1, 2, 3, 16, 17, 18))   // 5등
        ));

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        Map<Rank, Integer> rankCounts = statistics.getRankCounts();

        assertThat(rankCounts.get(Rank.FIFTH)).isEqualTo(3);
    }

    @Test
    @DisplayName("총 상금을 계산한다")
    void calculateTotalPrize() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),     // 3등 (1,500,000원)
                new Lotto(List.of(1, 2, 3, 4, 10, 11))    // 4등 (50,000원)
        ));

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        int totalPrize = statistics.getTotalPrize();

        assertThat(totalPrize).isEqualTo(1_550_000);  // 1,500,000 + 50,000
    }

    @Test
    @DisplayName("당첨이 없으면 총 상금은 0원이다")
    void calculateTotalPrizeWithNoWinning() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(10, 11, 12, 13, 14, 15)),  // 낙첨
                new Lotto(List.of(20, 21, 22, 23, 24, 25))   // 낙첨
        ));

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        int totalPrize = statistics.getTotalPrize();

        assertThat(totalPrize).isEqualTo(0);
    }

    @Test
    @DisplayName("수익률을 계산한다 (소수점 둘째 자리 반올림)")
    void calculateProfitRate() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12))  // 5등 (5,000원)
        ));
        Money purchaseAmount = new Money(8000);  // 8000원 구입

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        double profitRate = statistics.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(62.5);  // 5000 / 8000 * 100 = 62.5%
    }

    @Test
    @DisplayName("수익률 계산시 소수점 둘째 자리에서 반올림한다")
    void calculateProfitRateWithRounding() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12))  // 5등 (5,000원)
        ));
        Money purchaseAmount = new Money(7000);  // 7000원 구입

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        double profitRate = statistics.calculateProfitRate(purchaseAmount);

        // 5000 / 7000 * 100 = 71.428... → 71.4% (소수점 둘째 자리 반올림)
        assertThat(profitRate).isEqualTo(71.4);
    }

    @Test
    @DisplayName("손해인 경우 수익률은 100% 미만이다")
    void calculateProfitRateWithLoss() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12))  // 5등 (5,000원)
        ));
        Money purchaseAmount = new Money(10000);  // 10000원 구입

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        double profitRate = statistics.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(50.0);  // 5000 / 10000 * 100 = 50%
    }

    @Test
    @DisplayName("수익인 경우 수익률은 100% 이상이다")
    void calculateProfitRateWithProfit() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 4, 10, 11))  // 4등 (50,000원)
        ));
        Money purchaseAmount = new Money(5000);  // 5000원 구입

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        double profitRate = statistics.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(1000.0);  // 50000 / 5000 * 100 = 1000%
    }

    @Test
    @DisplayName("당첨이 없으면 수익률은 0%이다")
    void calculateProfitRateWithNoWinning() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(10, 11, 12, 13, 14, 15))  // 낙첨
        ));
        Money purchaseAmount = new Money(1000);

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);
        double profitRate = statistics.calculateProfitRate(purchaseAmount);

        assertThat(profitRate).isEqualTo(0.0);
    }

    @Test
    @DisplayName("특정 등수의 당첨 개수를 조회한다")
    void getCountByRank() {
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12)),  // 5등
                new Lotto(List.of(1, 2, 3, 13, 14, 15))   // 5등
        ));

        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);

        assertThat(statistics.getCountByRank(Rank.FIFTH)).isEqualTo(2);
        assertThat(statistics.getCountByRank(Rank.FOURTH)).isEqualTo(0);
        assertThat(statistics.getCountByRank(Rank.FIRST)).isEqualTo(0);
    }

    @Test
    @DisplayName("요구사항 예시: 8000원으로 5등 1개 당첨시 수익률 62.5%")
    void exampleFromRequirement() {
        // Given: 8000원 구입 (8개 로또)
        WinningLotto winningLotto = new WinningLotto(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                7
        );
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(8, 21, 23, 41, 42, 43)),   // 낙첨
                new Lotto(List.of(3, 5, 11, 16, 32, 38)),    // 낙첨
                new Lotto(List.of(7, 11, 16, 35, 36, 44)),   // 낙첨
                new Lotto(List.of(1, 8, 11, 31, 41, 42)),    // 낙첨
                new Lotto(List.of(13, 14, 16, 38, 42, 45)),  // 낙첨
                new Lotto(List.of(7, 11, 30, 40, 42, 43)),   // 낙첨
                new Lotto(List.of(2, 13, 22, 32, 38, 45)),   // 낙첨
                new Lotto(List.of(1, 3, 5, 14, 22, 45))      // 5등 (3개 일치)
        ));
        Money purchaseAmount = new Money(8000);

        // When
        WinningStatistics statistics = new WinningStatistics(lottoTickets, winningLotto);

        // Then
        assertThat(statistics.getCountByRank(Rank.FIFTH)).isEqualTo(1);
        assertThat(statistics.getTotalPrize()).isEqualTo(5_000);
        assertThat(statistics.calculateProfitRate(purchaseAmount)).isEqualTo(62.5);
    }
}