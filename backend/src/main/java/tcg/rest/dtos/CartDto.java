package tcg.rest.dtos;

import jakarta.validation.constraints.Positive;

public class CartDto {

    private Long cardStockId;
    
    @Positive(message = "Quantity must be positive")
    private int quantity;

    public CartDto() {}

    public CartDto(Long cardStockId, int quantity) {
        this.cardStockId = cardStockId;
        this.quantity = quantity;
    }

    public Long getCardStockId() {
        return cardStockId;
    }

    public void setCardStockId(Long cardStockId) {
        this.cardStockId = cardStockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
