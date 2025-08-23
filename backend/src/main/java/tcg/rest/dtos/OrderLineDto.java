package tcg.rest.dtos;

import java.math.BigDecimal;

public class OrderLineDto {

    private Long id;
    private Long orderId;
    private String card;
    private Long cardId;
    private String cardSetName;
    private String language;
    private BigDecimal price;
    private int quantity;
    private String condition;
    private Long sellerId;
    private String sellerName;
    private String tcgName;

    
    public OrderLineDto() {
    }
    
    public OrderLineDto(Long id, Long orderId, String card, Long cardId, String language, BigDecimal price,
            int quantity, String condition, Long sellerId, String sellerName, String cardSetName, String tcgName) {
        this.id = id;
        this.orderId = orderId;
        this.card = card;
        this.cardId = cardId;
        this.language = language;
        this.price = price;
        this.quantity = quantity;
        this.condition = condition;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.cardSetName = cardSetName;
        this.tcgName = tcgName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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

    @Override
    public String toString() {
        return "OrderLineDto{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", card='" + card + '\'' +
                ", cardId=" + cardId +
                ", language='" + language + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", condition='" + condition + '\'' +
                '}';
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getCardSetName() {
        return cardSetName;
    }

    public void setCardSetName(String cardSetName) {
        this.cardSetName = cardSetName;
    }

    public String getTcgName() {
        return tcgName;
    }

    public void setTcgName(String tcgName) {
        this.tcgName = tcgName;
    }
}
