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
        Lotto winningNumbers = readWinningNumbers();
        int bonusNumber = readBonusNumber();
        return createWinningLotto(winningNumbers, bonusNumber);
    }

    private Lotto readWinningNumbers() {
        while (true) {
            try {
                List<Integer> numbers = inputView.readWinningNumbers();
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private int readBonusNumber() {
        while (true) {
            try {
                return inputView.readBonusNumber();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private WinningLotto createWinningLotto(Lotto winningNumbers, int bonusNumber) {
        while (true) {
            try {
                return new WinningLotto(winningNumbers, bonusNumber);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                bonusNumber = readBonusNumber();
            }
        }
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