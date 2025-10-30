package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.util.LottoParser;

public class InputView {
    private static final String PURCHASE_AMOUNT_INPUT_MESSAGE = "구입금액을 입력해 주세요.";

    public int readPurchaseAmount() {
        System.out.println(PURCHASE_AMOUNT_INPUT_MESSAGE);
        String input = Console.readLine();
        return LottoParser.from(input);
    }

}
