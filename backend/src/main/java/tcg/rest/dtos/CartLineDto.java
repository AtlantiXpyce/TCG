package tcg.rest.dtos;

import java.math.BigDecimal;

public class CartLineDto {
    private Long cartId;
    private Long cardStockId;
    private String cardName;
    private String cardSetName;
    private String tcgName;
    private BigDecimal price;
    private int quantity;
    private String condition;
    private String language;
    private Long sellerId;
    private String sellerName;

    public CartLineDto() {}

    public CartLineDto(Long cartId, Long cardStockId, String cardName, String cardSetName, String tcgName,
                       BigDecimal price, int quantity, String condition, String language, Long sellerId, String sellerName) {
        this.cartId = cartId;
        this.cardStockId = cardStockId;
        this.cardName = cardName;
        this.cardSetName = cardSetName;
        this.tcgName = tcgName;
        this.price = price;
        this.quantity = quantity;
        this.condition = condition;
        this.language = language;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCardStockId() {
        return cardStockId;
    }

    public void setCardStockId(Long cardStockId) {
        this.cardStockId = cardStockId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
}
