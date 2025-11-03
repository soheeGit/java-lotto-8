package lotto.exception;

public class ErrorMessage {
    private static final String ERROR_PREFIX = "[ERROR] ";
    public static final String INVALID_AMOUNT = ERROR_PREFIX + "구입 금액은 1,000원 단위로 입력해야 합니다.";
    public static final String INVALID_LOTTO_SIZE = ERROR_PREFIX + "로또 번호는 6개여야 합니다.";
    public static final String INVALID_AMOUNT_POSITIVE = ERROR_PREFIX + "구입 금액은 양수여야 합니다.";
    public static final String INVALID_LOTTO_RANGE = ERROR_PREFIX + "로또 번호는 1부터 45 사이의 숫자여야 합니다.";
    public static final String INVALID_LOTTO_DUPLICATE = ERROR_PREFIX + "로또 번호는 중복될 수 없습니다.";
    public static final String INVALID_NUMBER_FORMAT = ERROR_PREFIX + "숫자 형식이 올바르지 않습니다.";
    public static final String EMPTY_INPUT = ERROR_PREFIX + "입력값이 비어있습니다.";
    public static final String INVALID_BONUS_LOTTO_RANGE = ERROR_PREFIX + "보너스 번호는 1부터 45 사이의 숫자여야 합니다.";
    public static final String INVALID_BONUS_LOTTO_DUPLICATE = ERROR_PREFIX + "보너스 번호는 당첨 번호와 중복될 수 없습니다.";
}
