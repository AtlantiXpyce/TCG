package tcg.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.Card;
import tcg.model.entities.CardDao;
import tcg.model.exceptions.CardNotFoundException;

@Service
@Transactional
public class CardServiceImpl implements CardService{

    @Autowired
    private CardDao cardDao;

    @Override
    public Card getCardById(Long id) throws CardNotFoundException {
        return cardDao.findById(id).orElseThrow(CardNotFoundException::new);
    }

    @Override
    public Page<Card> findCards(String name, Long setId, Long tcgId, Pageable pageable) {
        String searchName = (name == null || name.isEmpty()) ? "" : "%" + name + "%";
        return cardDao.searchCards(searchName, setId, tcgId, pageable);
    }
}
