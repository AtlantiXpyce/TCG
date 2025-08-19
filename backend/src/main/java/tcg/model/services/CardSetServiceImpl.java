package tcg.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.CardSet;
import tcg.model.entities.CardSetDao;

@Service
@Transactional
public class CardSetServiceImpl implements CardSetService{

    @Autowired
    private CardSetDao cardSetDao;

    @Override
    public List<CardSet> getAllCardSets() {
        return cardSetDao.findAll();
    }
}
