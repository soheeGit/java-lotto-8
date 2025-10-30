package lotto;

import lotto.config.AppConfig;
import lotto.controller.LottoController;

public class Application {
    public static void main(String[] args) {
        LottoController controller = AppConfig.createController();
        controller.run();
    }
}
