package tcg.model.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tcg.model.entities.CardLanguage;
import tcg.model.entities.CardLanguageDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardLanguageServiceImplTest {

    @Mock
    private CardLanguageDao cardLanguageDao;

    @InjectMocks
    private CardLanguageServiceImpl cardLanguageService;

    @Test
    void getAllLanguages_returnsList() {
        List<CardLanguage> mockLanguages = List.of(new CardLanguage(), new CardLanguage());
        when(cardLanguageDao.findAll()).thenReturn(mockLanguages);

        List<CardLanguage> result = cardLanguageService.getAllLanguages();

        assertEquals(2, result.size());
    }
}
