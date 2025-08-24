package tcg.model.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tcg.model.entities.Card;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.NotAnAdminException;
import tcg.model.exceptions.UserNotFoundException;

public interface CardService {
    public Card getCardById(Long id) throws CardNotFoundException;

    Page<Card> findCards(String name, Long setId, Long tcgId, Pageable pageable);

    void deleteCard(Long cardId, Long adminId) throws UserNotFoundException, NotAnAdminException, CardNotFoundException;
}
