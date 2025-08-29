package tcg.model.services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import tcg.model.entities.CardSet;
import tcg.model.entities.CardSetDao;

@ExtendWith(MockitoExtension.class)
class CardSetServiceImplTest {

    @Mock
    private CardSetDao cardSetDao;

    @InjectMocks
    private CardSetServiceImpl cardSetService;

    @Test
    void getAllCardSets_returnsList() {
        List<CardSet> mockSets = List.of(new CardSet(), new CardSet(), new CardSet());
        when(cardSetDao.findAll()).thenReturn(mockSets);

        List<CardSet> result = cardSetService.getAllCardSets();

        assertEquals(3, result.size());
    }
}
