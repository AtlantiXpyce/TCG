package tcg.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CardDaoTest {

    @Autowired
    private CardDao cardDao;

    @Test
    void testFindByName() {
        var card = cardDao.findByName("Pikachu").orElseThrow();
        assertThat(card.getCardNumber()).isEqualTo("58");
        assertThat(card.getSet().getName()).isEqualTo("Base Set");
    }

    @Test
    void testFindAll() {
        var cards = cardDao.findAll();
        assertThat(cards).isNotEmpty();
        assertThat(cards).extracting("name").contains("Charizard", "Black lotus");
    }
}
