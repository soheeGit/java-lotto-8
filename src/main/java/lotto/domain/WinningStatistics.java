package lotto.domain;

import java.util.EnumMap;
import java.util.Map;

public class WinningStatistics {
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
        for (Lotto lotto : lottoTickets.getLottos()) {
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
        double rate = (totalPrize / purchaseAmount.getAmount()) * 100;
        return Math.round(rate * 10) / 10.0;
    }
}