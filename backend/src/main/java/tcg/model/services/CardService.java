package tcg.model.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tcg.model.entities.Card;
import tcg.model.exceptions.CardNotFoundException;

public interface CardService {
    public Card getCardById(Long id) throws CardNotFoundException;

    Page<Card> findCards(String name, Long setId, Long tcgId, Pageable pageable);
}
