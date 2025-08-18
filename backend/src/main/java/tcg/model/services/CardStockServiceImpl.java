package tcg.model.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.CardLanguage;
import tcg.model.entities.CardStock;
import tcg.model.entities.CardStockDao;
import tcg.model.exceptions.CardNotFoundException;

@Service
@Transactional
public class CardStockServiceImpl implements CardStockService{

    @Autowired
    private CardStockDao cardStockDao;

    @Override
    public CardStock getById(Long id) throws CardNotFoundException{
        return cardStockDao.findById(id).orElseThrow(CardNotFoundException::new);
    }
    

    //Yet to implement
    @Override
    public Page<CardStock> findStock(Long id, String language, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CardStock createStock(Long cardId, Long sellerId, CardLanguage language, BigDecimal price, int quantity, String condition, String comment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
