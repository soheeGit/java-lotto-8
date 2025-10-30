package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LottoTicketsTest {
    @Test
    @DisplayName("여러 로또를 관리한다")
    void createLottoTickets() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(7, 8, 9, 10, 11, 12))
        );

        LottoTickets tickets = new LottoTickets(lottos);

        assertThat(tickets.getLottos()).hasSize(2);
    }
}
