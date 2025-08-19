package tcg.rest.dtos;

import java.math.BigDecimal;

public class AddCardStockDto {

    private Long cardId;
    private Long languageId;
    private BigDecimal price;
    private int quantity;
    private Long conditionId;
    private String comment;

    public AddCardStockDto() {}

    public AddCardStockDto(Long cardId, Long languageId, BigDecimal price, int quantity, Long conditionId, String comment) {
        this.cardId = cardId;
        this.languageId = languageId;
        this.price = price;
        this.quantity = quantity;
        this.conditionId = conditionId;
        this.comment = comment;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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
