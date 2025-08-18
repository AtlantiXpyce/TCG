package tcg.rest.dtos;

import java.math.BigDecimal;

public class CardStockDto {

    private Long id;
    private Long cardId;
    private Long sellerId;
    private Long languageId;
    private BigDecimal price;
    private int quantity;
    private Long conditionId;
    private String comment;

    public CardStockDto() {}

    public CardStockDto(Long id, Long cardId, Long sellerId, Long languageId, BigDecimal price, int quantity, Long conditionId, String comment) {
        this.id = id;
        this.cardId = cardId;
        this.sellerId = sellerId;
        this.languageId = languageId;
        this.price = price;
        this.quantity = quantity;
        this.conditionId = conditionId;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguage(Long languageId) {
        this.languageId = languageId;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
