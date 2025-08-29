package tcg.model.services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import tcg.model.entities.OrderLineDao;
import tcg.model.entities.OrderTable;
import tcg.model.entities.OrderTableDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.AlreadyReviewedException;
import tcg.model.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock private OrderTableDao orderTableDao;
    @Mock private OrderLineDao orderLineDao;
    @Mock private UserDao userDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User buyer;
    private User seller;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        buyer = new User();
        buyer.setId(1L);

        seller = new User();
        seller.setId(2L);

        orderTable = new OrderTable();
        orderTable.setId(10L);
        orderTable.setBuyer(buyer);
        orderTable.setSeller(seller);
    }

    @Test
    void getOrdersForBuyer_success() throws UserNotFoundException {
        when(userDao.findById(1L)).thenReturn(Optional.of(buyer));
        when(orderTableDao.findByBuyerIdOrderByIdDesc(1L)).thenReturn(List.of(orderTable));

        List<OrderTable> result = orderService.getOrdersForBuyer(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getOrdersForBuyer_userNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> orderService.getOrdersForBuyer(1L));
    }

    @Test
    void reviewOrder_success() throws Exception {
        when(userDao.findById(1L)).thenReturn(Optional.of(buyer));
        when(orderTableDao.findById(10L)).thenReturn(Optional.of(orderTable));
        orderService.reviewOrder(10L, 5, "Good", 1L);

        assertEquals(5, orderTable.getRating());
        assertEquals("Good", orderTable.getReview());
        assertNotNull(orderTable.getArrivalDate());
        verify(orderTableDao).save(orderTable);
    }

    @Test
    void reviewOrder_alreadyReviewed_throws() {
        orderTable.setReview("prev");
        when(userDao.findById(1L)).thenReturn(Optional.of(buyer));
        when(orderTableDao.findById(10L)).thenReturn(Optional.of(orderTable));

        assertThrows(AlreadyReviewedException.class, () -> orderService.reviewOrder(10L, 5, "Good", 1L));
    }

    @Test
    void orderSent_success() throws Exception {
        when(userDao.findById(2L)).thenReturn(Optional.of(seller));
        when(orderTableDao.findById(10L)).thenReturn(Optional.of(orderTable));

        orderService.orderSent(2L, 10L);

        assertTrue(orderTable.getIsSent());
        verify(orderTableDao).save(orderTable);
    }
}
