package lotto.domain;

import java.util.List;

public record LottoTickets(List<Lotto> lottos) {
    public LottoTickets {
        lottos = List.copyOf(lottos);
    }
}
