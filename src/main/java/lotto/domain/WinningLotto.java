package lotto.domain;

public class WinningLotto {
    private final Lotto winningNumbers;
    private final int bonusNumber;

    public WinningLotto(Lotto winningNumbers, int bonusNumber) {
        validateBonusRange(bonusNumber);
        validateBonusDuplicate(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusRange(int bonusNumber) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private void validateBonusDuplicate(Lotto winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public Rank match(Lotto lotto) {
        int matchCount = winningNumbers.countMatches(lotto.getNumbers());
        boolean bonusMatch = lotto.contains(bonusNumber);
        return Rank.valueOf(matchCount, bonusMatch);
    }

    public Lotto getWinningNumbers() {
        return winningNumbers;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }
}