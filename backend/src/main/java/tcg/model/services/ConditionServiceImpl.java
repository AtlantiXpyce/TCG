package tcg.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.Condition;
import tcg.model.entities.ConditionDao;

@Service
@Transactional
public class ConditionServiceImpl implements ConditionService{
    
    @Autowired
    private ConditionDao conditionDao;

    @Override
    public List<Condition> getAllConditions() {
        return conditionDao.findAll();
    }
    
}
