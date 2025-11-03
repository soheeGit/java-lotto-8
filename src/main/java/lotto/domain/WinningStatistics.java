package lotto.domain;

import java.util.EnumMap;
import java.util.Map;

public class WinningStatistics {
    private static final int PERCENTAGE = 100;
    private static final int DECIMAL_SCALE = 10;

    private final Map<Rank, Integer> rankCounts;

    public WinningStatistics(LottoTickets lottoTickets, WinningLotto winningLotto) {
        this.rankCounts = initializeRankCounts();
        calculateStatistics(lottoTickets, winningLotto);
    }

    private Map<Rank, Integer> initializeRankCounts() {
        Map<Rank, Integer> counts = new EnumMap<>(Rank.class);
        for (Rank rank : Rank.values()) {
            if (rank.isWinning()) {
                counts.put(rank, 0);
            }
        }
        return counts;
    }

    private void calculateStatistics(LottoTickets lottoTickets, WinningLotto winningLotto) {
        for (Lotto lotto : lottoTickets.lottos()) {
            Rank rank = winningLotto.match(lotto);
            if (rank.isWinning()) {
                rankCounts.put(rank, rankCounts.get(rank) + 1);
            }
        }
    }

    public Map<Rank, Integer> getRankCounts() {
        return new EnumMap<>(rankCounts);
    }

    public int getCountByRank(Rank rank) {
        return rankCounts.getOrDefault(rank, 0);
    }

    public int getTotalPrize() {
        return rankCounts.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrize() * entry.getValue())
                .sum();
    }

    public double calculateProfitRate(Money purchaseAmount) {
        double totalPrize = getTotalPrize();
        double rate = (totalPrize / purchaseAmount.amount()) * PERCENTAGE;
        return Math.round(rate * DECIMAL_SCALE) / (double) DECIMAL_SCALE;
    }
}