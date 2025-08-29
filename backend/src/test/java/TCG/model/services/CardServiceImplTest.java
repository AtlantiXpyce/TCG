package tcg.model.services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import tcg.model.entities.Card;
import tcg.model.entities.CardDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.NotAnAdminException;
import tcg.model.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock private CardDao cardDao;
    @Mock private UserDao userDao;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card card;
    private User admin;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setId(1L);

        admin = new User();
        admin.setId(10L);
        admin.setRole("ADMIN");
    }

    @Test
    void getCardById_success() throws CardNotFoundException {
        when(cardDao.findById(1L)).thenReturn(Optional.of(card));
        Card result = cardService.getCardById(1L);
        assertEquals(card, result);
    }

    @Test
    void getCardById_notFound() {
        when(cardDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.getCardById(1L));
    }

    @Test
    void deleteCard_success() throws Exception {
        when(cardDao.findById(1L)).thenReturn(Optional.of(card));
        when(userDao.findById(10L)).thenReturn(Optional.of(admin));

        cardService.deleteCard(1L, 10L);

        verify(cardDao).delete(card);
    }

    @Test
    void deleteCard_cardNotFound() {
        when(cardDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.deleteCard(1L, 10L));
    }

    @Test
    void deleteCard_userNotFound() {
        when(cardDao.findById(1L)).thenReturn(Optional.of(card));
        when(userDao.findById(10L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> cardService.deleteCard(1L, 10L));
    }

    @Test
    void deleteCard_notAdmin() {
        User user = new User();
        user.setRole("USER");
        when(cardDao.findById(1L)).thenReturn(Optional.of(card));
        when(userDao.findById(10L)).thenReturn(Optional.of(user));
        assertThrows(NotAnAdminException.class, () -> cardService.deleteCard(1L, 10L));
    }
}