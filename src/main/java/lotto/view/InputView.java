package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.util.LottoParser;

import java.util.List;

public class InputView {
    private static final String PURCHASE_AMOUNT_INPUT_MESSAGE = "구입금액을 입력해 주세요.";
    private static final String WINNING_NUMBERS_INPUT_MESSAGE = "당첨 번호를 입력해 주세요.";
    private static final String BONUS_NUMBER_INPUT_MESSAGE = "보너스 번호를 입력해 주세요.";

    public int readPurchaseAmount() {
        System.out.println(PURCHASE_AMOUNT_INPUT_MESSAGE);
        String input = Console.readLine();
        System.out.println();
        return LottoParser.from(input);
    }

    public List<Integer> readWinningNumbers() {
        System.out.println(WINNING_NUMBERS_INPUT_MESSAGE);
        String input = Console.readLine();
        System.out.println();
        return LottoParser.parseWinningNumbers(input);
    }

    public int readBonusNumber() {
        System.out.println(BONUS_NUMBER_INPUT_MESSAGE);
        String input = Console.readLine();
        return LottoParser.parseBonusNumber(input);
    }
}
