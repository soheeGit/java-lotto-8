package lotto.util;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;

import java.util.List;
import java.util.stream.IntStream;

public class LottoGenerator {
    private static final int LOTTO_START = 1;
    private static final int LOTTO_END = 45;
    private static final int LOTTO_SIZE = 6;

    private LottoGenerator() {}

    public static Lotto generate() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                LOTTO_START, LOTTO_END, LOTTO_SIZE
        );
        return new Lotto(numbers);
    }

    public static List<Lotto> generateMultiple(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generate())
                .toList();
    }
}
