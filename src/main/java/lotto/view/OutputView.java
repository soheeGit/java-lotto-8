package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoTickets;
import lotto.domain.Rank;
import lotto.domain.WinningStatistics;

import java.util.List;

public class OutputView {
    private static final String PURCHASE_COUNT_OUTPUT_MESSAGE = "%d개를 구매했습니다.";
    private static final String WINNING_STATISTICS_HEADER = "\n당첨 통계\n---";
    private static final String WINNING_STATISTICS_FORMAT = "%s (%,d원) - %d개%n";
    private static final String PROFIT_RATE_MESSAGE = "총 수익률은 %.1f%%입니다.";

    public void printPurchaseResult(int count) {
        System.out.printf(PURCHASE_COUNT_OUTPUT_MESSAGE + "%n", count);
    }

    public void printLottos(LottoTickets tickets) {
        for (Lotto lotto : tickets.lottos()) {
            List<Integer> numbers = lotto.getNumbers();
            System.out.println(numbers);
        }
        System.out.println();
    }

    public void printWinningStatistics(WinningStatistics statistics) {
        System.out.println(WINNING_STATISTICS_HEADER);

        for (Rank rank : Rank.values()) {
            if (rank.isWinning()) {
                int count = statistics.getCountByRank(rank);
                System.out.printf(WINNING_STATISTICS_FORMAT,
                        rank.getDescription(),
                        rank.getPrize(),
                        count
                );
            }
        }
    }

    public void printProfitRate(double profitRate) {
        System.out.printf(PROFIT_RATE_MESSAGE + "%n", profitRate);
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
