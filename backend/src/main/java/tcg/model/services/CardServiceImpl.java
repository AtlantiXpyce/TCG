package tcg.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcg.model.entities.Card;
import tcg.model.entities.CardDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.NotAnAdminException;
import tcg.model.exceptions.UserNotFoundException;

@Service
@Transactional
public class CardServiceImpl implements CardService{

    @Autowired
    private CardDao cardDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Card getCardById(Long id) throws CardNotFoundException {
        return cardDao.findById(id).orElseThrow(CardNotFoundException::new);
    }

    @Override
    public Page<Card> findCards(String name, Long setId, Long tcgId, Pageable pageable) {
        String searchName = (name == null || name.isEmpty()) ? "" : "%" + name + "%";
        return cardDao.searchCards(searchName, setId, tcgId, pageable);
    }

    @Override
    public void deleteCard(Long cardId, Long adminId) throws UserNotFoundException, NotAnAdminException, CardNotFoundException {
        Optional<Card> cardAux = cardDao.findById(cardId);
        Optional<User> adminAux = userDao.findById(adminId);
        
        if(cardAux.isEmpty()){ throw new CardNotFoundException(); }
        if(adminAux.isEmpty()){ throw new UserNotFoundException(); }
        if(!adminAux.get().getRole().equals("ADMIN")){ throw new NotAnAdminException(); }

        cardDao.delete(cardAux.get());
    }
}
