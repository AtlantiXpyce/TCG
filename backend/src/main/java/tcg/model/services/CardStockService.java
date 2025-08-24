package tcg.model.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tcg.model.entities.CardStock;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.UserNotFoundException;

public interface CardStockService {
    public CardStock getById(Long id) throws CardNotFoundException;

    public Page<CardStock> findStock(Long id, String language, Pageable pageable);

    public CardStock createStock(Long cardId, Long sellerId, Long languageId, BigDecimal price, int quantity, Long conditionId, String comment) throws CardNotFoundException, UserNotFoundException;

    public Page<CardStock> getSellerStocks(Long sellerId, Pageable pageable) throws UserNotFoundException;
}
