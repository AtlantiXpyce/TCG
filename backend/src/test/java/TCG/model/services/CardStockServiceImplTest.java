package tcg.model.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import tcg.model.entities.Card;
import tcg.model.entities.CardDao;
import tcg.model.entities.CardLanguage;
import tcg.model.entities.CardLanguageDao;
import tcg.model.entities.CardStock;
import tcg.model.entities.CardStockDao;
import tcg.model.entities.Condition;
import tcg.model.entities.ConditionDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.NotTheUserException;
import tcg.model.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class CardStockServiceImplTest {

    @Mock private CardStockDao cardStockDao;
    @Mock private CardDao cardDao;
    @Mock private UserDao userDao;
    @Mock private ConditionDao conditionDao;
    @Mock private CardLanguageDao cardLanguageDao;

    @InjectMocks
    private CardStockServiceImpl cardStockService;

    @Test
    void getById_found() throws CardNotFoundException {
        CardStock stock = new CardStock();
        when(cardStockDao.findById(1L)).thenReturn(Optional.of(stock));

        CardStock result = cardStockService.getById(1L);

        assertEquals(stock, result);
    }

    @Test
    void getById_notFound_throwsException() {
        when(cardStockDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardStockService.getById(1L));
    }

    @Test
    void findStock_returnsPage() {
        Page<CardStock> page = new PageImpl<>(List.of(new CardStock()));
        when(cardStockDao.findStock(eq(1L), any(), any(),any(Pageable.class))).thenReturn(page);

        Page<CardStock> result = cardStockService.findStock(1L, "", "", Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void createStock_success() throws Exception {
        Card card = new Card();
        User user = new User();
        CardLanguage lang = new CardLanguage();
        Condition cond = new Condition();
        CardStock stock = new CardStock();

        when(cardDao.findById(1L)).thenReturn(Optional.of(card));
        when(userDao.findById(2L)).thenReturn(Optional.of(user));
        when(cardLanguageDao.findById(3L)).thenReturn(Optional.of(lang));
        when(conditionDao.findById(4L)).thenReturn(Optional.of(cond));
        when(cardStockDao.save(any(CardStock.class))).thenReturn(stock);

        CardStock result = cardStockService.createStock(1L, 2L, 3L, BigDecimal.TEN, 5, 4L, "comment");

        assertNotNull(result);
        verify(cardStockDao).save(any(CardStock.class));
    }

    @Test
    void createStock_cardNotFound_throws() {
        when(cardDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> cardStockService.createStock(1L, 2L, 3L, BigDecimal.TEN, 5, 4L, "c"));
    }

    @Test
    void getSellerStocks_success() throws UserNotFoundException {
        when(userDao.findById(2L)).thenReturn(Optional.of(new User()));
        Page<CardStock> page = new PageImpl<>(List.of(new CardStock()));
        when(cardStockDao.findByUserId(eq(2L), any(Pageable.class))).thenReturn(page);

        Page<CardStock> result = cardStockService.getSellerStocks(2L, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getSellerStocks_userNotFound_throws() {
        when(userDao.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> cardStockService.getSellerStocks(2L, Pageable.unpaged()));
    }

    @Test
    void deleteStock_success_sellerOwnsStock() throws Exception {
        User seller = new User();
        seller.setId(2L);
        CardStock stock = new CardStock();
        stock.setSeller(seller);

        when(userDao.findById(2L)).thenReturn(Optional.of(seller));
        when(cardStockDao.findById(10L)).thenReturn(Optional.of(stock));

        cardStockService.deleteStock(10L, 2L);

        verify(cardStockDao).delete(stock);
    }

    @Test
    void deleteStock_notOwnerNotAdmin_throws() {
        User seller = new User();
        seller.setId(99L);
        seller.setRole("USER");

        User requestUser = new User();
        requestUser.setId(2L);
        requestUser.setRole("USER");

        CardStock stock = new CardStock();
        stock.setSeller(seller);

        when(userDao.findById(2L)).thenReturn(Optional.of(requestUser));
        when(cardStockDao.findById(10L)).thenReturn(Optional.of(stock));

        assertThrows(NotTheUserException.class,
                () -> cardStockService.deleteStock(10L, 2L));
    }

    @Test
    void deleteStock_adminCanDelete() throws Exception {
        User seller = new User();
        seller.setId(99L);

        User admin = new User();
        admin.setId(2L);
        admin.setRole("ADMIN");

        CardStock stock = new CardStock();
        stock.setSeller(seller);

        when(userDao.findById(2L)).thenReturn(Optional.of(admin));
        when(cardStockDao.findById(10L)).thenReturn(Optional.of(stock));

        cardStockService.deleteStock(10L, 2L);

        verify(cardStockDao).delete(stock);
    }
}
