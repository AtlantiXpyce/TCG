package tcg.model.services;

import tcg.model.entities.Card;
import tcg.model.exceptions.CardNotFoundException;

public interface CardService {
    public Card getCardById(Long id) throws CardNotFoundException;
}
