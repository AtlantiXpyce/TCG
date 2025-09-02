package tcg.model.entities;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class CartDaoTest {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByUserId() {
        var user = entityManager.find(User.class, 1L);
        var cardStock = entityManager.find(CardStock.class, 1L);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCardStock(cardStock);
        cart.setQuantity(3);
        entityManager.persist(cart);
        entityManager.flush();

        List<Cart> carts = cartDao.findByUserId(user.getId());
        assertThat(carts).isNotEmpty();
        assertThat(carts.get(0).getQuantity()).isEqualTo(3);
    }

    @Test
    void testFindByUserIdAndCardStockId() {
        var user = entityManager.find(User.class, 1L);
        var cardStock = entityManager.find(CardStock.class, 1L);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCardStock(cardStock);
        cart.setQuantity(2);
        entityManager.persist(cart);
        entityManager.flush();

        Optional<Cart> found = cartDao.findByUserIdAndCartStockId(user.getId(), cardStock.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getQuantity()).isEqualTo(2);
    }
}
