package tcg.rest.dtos;

import java.math.BigDecimal;

public class CardStockDto {

    private Long id;
    private Long cardId;
    private Long sellerId;
    private String language;
    private BigDecimal price;
    private int quantity;
    private String condition;
    private String comment;

    public CardStockDto() {}

    public CardStockDto(Long id, Long cardId, Long sellerId, String language,
                        BigDecimal price, int quantity, String condition, String comment) {
        this.id = id;
        this.cardId = cardId;
        this.sellerId = sellerId;
        this.language = language;
        this.price = price;
        this.quantity = quantity;
        this.condition = condition;
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

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
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

    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
}
