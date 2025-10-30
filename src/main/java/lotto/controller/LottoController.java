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
        // 1. 구입 금액 입력
        Money money = readMoney();

        // 2. 로또 생성
        int count = money.getLottoCount();
        List<Lotto> lottos = LottoGenerator.generateMultiple(count);
        LottoTickets tickets = new LottoTickets(lottos);

        // 3. 출력
        outputView.printPurchaseResult(count);
        outputView.printLottos(tickets);
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
