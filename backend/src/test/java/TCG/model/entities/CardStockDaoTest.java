package tcg.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class CardStockDaoTest {

    @Autowired
    private CardStockDao cardStockDao;

    @Test
    void testFindStockByCardId() {
        var page = cardStockDao.findStock(14L, "", "", PageRequest.of(0, 10));

        assertThat(page).isNotEmpty();
        assertThat(page.getContent()).allMatch(cs -> cs.getCard().getId() == 14L);

        var prices = page.getContent().stream().map(CardStock::getPrice).toList();
        assertThat(prices).isSorted();
    }

    void testFindStockByCardIdAndLanguage() {
        var page = cardStockDao.findStock(14L, "Inglés", "", PageRequest.of(0, 10));
        assertThat(page).isNotEmpty();
    }

    @Test
    void testFindStockByCardIdAndCondition() {
        var page = cardStockDao.findStock(14L, "", "Perfecta", PageRequest.of(0, 10));
        assertThat(page).isNotEmpty();
    }
}

