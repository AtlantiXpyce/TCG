package tcg.model.services;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import tcg.model.entities.Card;
import tcg.model.entities.CardLanguage;
import tcg.model.entities.CardStock;
import tcg.model.entities.CardStockDao;
import tcg.model.entities.Cart;
import tcg.model.entities.CartDao;
import tcg.model.entities.Condition;
import tcg.model.entities.OrderLine;
import tcg.model.entities.OrderLineDao;
import tcg.model.entities.OrderTable;
import tcg.model.entities.OrderTableDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.CardStockNotFoundException;
import tcg.model.exceptions.CartNotFoundException;
import tcg.model.exceptions.EmptyCartException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.QuantityExceededException;
import tcg.model.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock private CartDao cartDao;
    @Mock private CardStockDao cardStockDao;
    @Mock private UserDao userDao;
    @Mock private OrderTableDao orderTableDao;
    @Mock private OrderLineDao orderLineDao;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private User seller;
    private CardStock cardStock;
    private Cart cart;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        seller = new User();
        seller.setId(2L);

        CardLanguage lang = new CardLanguage();
        lang.setName("EN");
        Condition condition = new Condition();
        condition.setName("NM");
        Card cardEntity = new Card();
        cardEntity.setId(50L);

        cardStock = new CardStock();
        cardStock.setId(10L);
        cardStock.setQuantity(5);
        cardStock.setCartQuantity(0);
        cardStock.setSeller(seller);
        cardStock.setLang(lang);
        cardStock.setCondition(condition);
        cardStock.setCard(cardEntity);
        cardStock.setPrice(BigDecimal.TEN);

        cart = new Cart(cardStock, 1, user);
        cart.setId(100L);
    }

    // --- addToCart ---

    @Test
    void addToCart_success_newCart() throws Exception {
        when(cardStockDao.findById(10L)).thenReturn(Optional.of(cardStock));
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(cartDao.findByUserIdAndCartStockId(1L, 10L)).thenReturn(Optional.empty());
        when(cartDao.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart result = cartService.addToCart(10L, 2, 1L);

        assertEquals(2, result.getQuantity());
        assertEquals(3, cardStock.getQuantity());
        assertEquals(2, cardStock.getCartQuantity());
        verify(cardStockDao).save(cardStock);
        verify(cartDao).save(result);
    }

    @Test
    void addToCart_success_existingCart() throws Exception {
        when(cardStockDao.findById(10L)).thenReturn(Optional.of(cardStock));
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(cartDao.findByUserIdAndCartStockId(1L, 10L)).thenReturn(Optional.of(cart));
        when(cartDao.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart result = cartService.addToCart(10L, 2, 1L);

        assertEquals(3, result.getQuantity()); // 1 + 2
        assertEquals(3, cardStock.getQuantity());
        assertEquals(2, cardStock.getCartQuantity());
        verify(cardStockDao).save(cardStock);
        verify(cartDao).save(result);
    }

    @Test
    void addToCart_quantityTooLow_throws() {
        assertThrows(InvalidParameterException.class, () -> cartService.addToCart(10L, 0, 1L));
    }

    @Test
    void addToCart_cardStockNotFound_throws() {
        when(cardStockDao.findById(10L)).thenReturn(Optional.empty());
        assertThrows(CardStockNotFoundException.class, () -> cartService.addToCart(10L, 1, 1L));
    }

    @Test
    void addToCart_userNotFound_throws() {
        when(cardStockDao.findById(10L)).thenReturn(Optional.of(cardStock));
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> cartService.addToCart(10L, 1, 1L));
    }

    @Test
    void addToCart_quantityExceeded_throws() {
        cardStock.setQuantity(1);
        when(cardStockDao.findById(10L)).thenReturn(Optional.of(cardStock));
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(QuantityExceededException.class, () -> cartService.addToCart(10L, 2, 1L));
    }

    // --- getCart ---

    @Test
    void getCart_success() throws UserNotFoundException {
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(cart));

        List<Cart> result = cartService.getCart(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getCart_userNotFound_throws() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> cartService.getCart(1L));
    }

    // --- deleteFromCart ---

    @Test
    void deleteFromCart_success() throws Exception {
        cart.setQuantity(2);
        cardStock.setCartQuantity(2);
        cardStock.setQuantity(3);

        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(cartDao.findById(100L)).thenReturn(Optional.of(cart));

        cartService.deleteFromCart(100L, 1L);

        assertEquals(5, cardStock.getQuantity());
        assertEquals(0, cardStock.getCartQuantity());
        verify(cartDao).delete(cart);
    }

    @Test
    void deleteFromCart_userNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> cartService.deleteFromCart(100L, 1L));
    }

    @Test
    void deleteFromCart_cartNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(cartDao.findById(100L)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundException.class, () -> cartService.deleteFromCart(100L, 1L));
    }

    @Test
    void deleteFromCart_notTheUser() {
        User otherUser = new User();
        otherUser.setId(99L);
        when(userDao.findById(1L)).thenReturn(Optional.of(otherUser));
        when(cartDao.findById(100L)).thenReturn(Optional.of(cart));

        assertThrows(NotTheUserException.class, () -> cartService.deleteFromCart(100L, 1L));
    }

    // --- buyCart ---

    @Test
    void buyCart_success() throws Exception {
        cart.setQuantity(2);
        cardStock.setCartQuantity(2);

        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(cartDao.findByUserId(1L)).thenReturn(List.of(cart));
        when(orderTableDao.save(any(OrderTable.class))).thenAnswer(inv -> inv.getArgument(0));
        when(orderLineDao.save(any(OrderLine.class))).thenAnswer(inv -> inv.getArgument(0));

        List<OrderLine> result = cartService.buyCart(1L);

        assertEquals(1, result.size());
        assertEquals(0, cardStock.getCartQuantity());
        verify(orderTableDao).save(any(OrderTable.class));
        verify(orderLineDao).save(any(OrderLine.class));
        verify(cartDao).delete(cart);
    }

    @Test
    void buyCart_userNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> cartService.buyCart(1L));
    }

    @Test
    void buyCart_emptyCart() {
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(cartDao.findByUserId(1L)).thenReturn(List.of());
        assertThrows(EmptyCartException.class, () -> cartService.buyCart(1L));
    }
}
