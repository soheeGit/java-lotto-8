package lotto.domain;

import lotto.validator.LottoValidator;

public record WinningLotto(Lotto winningNumbers, int bonusNumber) {
    public WinningLotto {
        LottoValidator.validateBonusRange(bonusNumber);
        LottoValidator.validateBonusDuplicate(winningNumbers, bonusNumber);
    }

    public Rank match(Lotto lotto) {
        int matchCount = winningNumbers.countMatches(lotto.getNumbers());
        boolean bonusMatch = lotto.contains(bonusNumber);
        return Rank.valueOf(matchCount, bonusMatch);
    }
}