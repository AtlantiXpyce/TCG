package tcg.model.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tcg.model.entities.CardLanguage;
import tcg.model.entities.CardStock;
import tcg.model.exceptions.CardNotFoundException;

public interface CardStockService {
    public CardStock getById(Long id) throws CardNotFoundException;

    public Page<CardStock> findStock(Long id, String language, Pageable pageable);

    public CardStock createStock(Long cardId, Long sellerId, CardLanguage language, BigDecimal price, int quantity, String condition, String comment);
    /*
    private Long id;
    private Card card;
    private User seller;
    private BigDecimal price;
    private int quantity;
    private String condition;
    private String comment;
    private CardLanguage lang;
     */
}
