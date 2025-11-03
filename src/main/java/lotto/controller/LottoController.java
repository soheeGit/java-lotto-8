package lotto.controller;

import lotto.domain.*;
import lotto.util.LottoGenerator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Money money = readMoney();

        int count = money.getLottoCount();
        List<Lotto> lottos = LottoGenerator.generateMultiple(count);
        LottoTickets tickets = new LottoTickets(lottos);

        outputView.printPurchaseResult(count);
        outputView.printLottos(tickets);

        WinningLotto winningLotto = readWinningLotto();
        WinningStatistics statistics = new WinningStatistics(tickets, winningLotto);

        outputView.printWinningStatistics(statistics);
        outputView.printProfitRate(statistics.calculateProfitRate(money));
    }

    private WinningLotto readWinningLotto() {
        while (true) {
            try {
                Lotto winningNumbers = readWinningNumbers();
                int bonusNumber = readBonusNumber();
                return new WinningLotto(winningNumbers, bonusNumber);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Lotto readWinningNumbers() {
        List<Integer> numbers = inputView.readWinningNumbers();
        return new Lotto(numbers);
    }

    private int readBonusNumber() {
        return inputView.readBonusNumber();
    }

    private Money readMoney() {
        while (true) {
            try {
                int amount = inputView.readPurchaseAmount();
                return new Money(amount);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
