package tcg.model.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import tcg.model.exceptions.UserNotFoundException;

@Service
@Transactional
public class CardStockServiceImpl implements CardStockService{

    @Autowired
    private CardStockDao cardStockDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConditionDao conditionDao;

    @Autowired
    private CardLanguageDao cardLanguageDao;

    @Override
    public CardStock getById(Long id) throws CardNotFoundException{
        return cardStockDao.findById(id).orElseThrow(CardNotFoundException::new);
    }
    
    @Override
    public Page<CardStock> findStock(Long id, String language, Pageable pageable) {
        return cardStockDao.findStock(id, (language == null || language.isBlank()) ? null : language, pageable);
    }

    @Override
    public CardStock createStock(Long cardId, Long sellerId, Long languageId, BigDecimal price, int quantity, Long conditionId, String comment) throws CardNotFoundException, UserNotFoundException {
        Card card = cardDao.findById(cardId).orElseThrow(CardNotFoundException::new);
        User seller = userDao.findById(sellerId).orElseThrow(UserNotFoundException::new);
        CardLanguage lang = cardLanguageDao.findById(languageId).orElseThrow(() -> new IllegalArgumentException("Language not found"));
        Condition condition = conditionDao.findById(conditionId).orElseThrow(() -> new IllegalArgumentException("Condition not found"));
        
        CardStock stock = new CardStock(card, seller, price, quantity, condition, comment, lang);
        return cardStockDao.save(stock);
    }
}
