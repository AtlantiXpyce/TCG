package tcg.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class OrderTableDaoTest {

    @Autowired
    private OrderTableDao orderTableDao;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByBuyerIdOrderByIdDesc() {
        var buyer = entityManager.find(User.class, 1L);
        var seller = entityManager.find(User.class, 3L);

        OrderTable order1 = new OrderTable();
        order1.setBuyer(buyer);
        order1.setSeller(seller);
        order1.setIsSent(false);
        order1.setRating(5);
        order1.setReview("Excelente");
        order1.setOrderDate(LocalDateTime.now());
        entityManager.persist(order1);

        OrderTable order2 = new OrderTable();
        order2.setBuyer(buyer);
        order2.setSeller(seller);
        order2.setIsSent(false);
        order2.setRating(4);
        order2.setReview("Bueno");
        order2.setOrderDate(LocalDateTime.now());
        entityManager.persist(order2);

        entityManager.flush();

        List<OrderTable> orders = orderTableDao.findByBuyerIdOrderByIdDesc(buyer.getId());
        assertThat(orders).hasSize(2);
        assertThat(orders.get(0).getId()).isGreaterThan(orders.get(1).getId());
    }

    @Test
    void testFindBySellerIdOrderByIdDesc() {
        var buyer = entityManager.find(User.class, 1L);
        var seller = entityManager.find(User.class, 3L);

        OrderTable order = new OrderTable();
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setIsSent(true);
        order.setRating(5);
        order.setReview("Perfecto");
        order.setOrderDate(LocalDateTime.now());
        entityManager.persist(order);

        entityManager.flush();

        List<OrderTable> orders = orderTableDao.findBySellerIdOrderByIdDesc(seller.getId());
        assertThat(orders).isNotEmpty();
        assertThat(orders.get(0).getSeller().getId()).isEqualTo(seller.getId());
    }

    @Test
    void testFindAverageRatingForSeller() {
        var buyer = entityManager.find(User.class, 1L);
        var seller = entityManager.find(User.class, 3L);

        OrderTable order1 = new OrderTable();
        order1.setBuyer(buyer);
        order1.setSeller(seller);
        order1.setIsSent(true);
        order1.setRating(5);
        order1.setReview("Excelente");
        order1.setOrderDate(LocalDateTime.now());
        entityManager.persist(order1);

        OrderTable order2 = new OrderTable();
        order2.setBuyer(buyer);
        order2.setSeller(seller);
        order2.setIsSent(true);
        order2.setRating(3);
        order2.setReview("Regular");
        order2.setOrderDate(LocalDateTime.now());
        entityManager.persist(order2);

        entityManager.flush();

        Double avg = orderTableDao.findAverageRatingForSeller(seller.getId());
        assertThat(avg).isEqualTo(4.0);
    }
}
