package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoTickets;

import java.util.List;

public class OutputView {
    private static final String PURCHASE_COUNT_OUTPUT_MESSAGE = "%d개를 구매했습니다.";

    public void printPurchaseResult(int count) {
        System.out.printf(PURCHASE_COUNT_OUTPUT_MESSAGE + "%n", count);
    }

    public void printLottos(LottoTickets tickets) {
        for (Lotto lotto : tickets.getLottos()) {
            List<Integer> numbers = lotto.getNumbers();
            System.out.println(numbers);
        }
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
