package tcg.model.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tcg.model.entities.Tcg;
import tcg.model.entities.TcgDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TcgServiceImplTest {

    @Mock private TcgDao tcgDao;

    @InjectMocks
    private TcgServiceImpl tcgService;

    @Test
    void getAllTcgs_success() {
        List<Tcg> mockTcgs = List.of(new Tcg(), new Tcg());
        when(tcgDao.findAll()).thenReturn(mockTcgs);

        List<Tcg> result = tcgService.getAllTcgs();

        assertEquals(2, result.size());
    }
}
