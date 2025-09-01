package tcg.model.services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import tcg.model.entities.Condition;
import tcg.model.entities.ConditionDao;

@ExtendWith(MockitoExtension.class)
class ConditionServiceImplTest {

    @Mock
    private ConditionDao conditionDao;

    @InjectMocks
    private ConditionServiceImpl conditionService;

    @Test
    void getAllConditions_returnsList() {
        List<Condition> mockConditions = List.of(new Condition(), new Condition());
        when(conditionDao.findAll()).thenReturn(mockConditions);

        List<Condition> result = conditionService.getAllConditions();

        assertEquals(2, result.size());
    }
}
