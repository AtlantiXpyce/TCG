package tcg.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
public class CardStock {

    private Long id;
    private Card card;
    private User seller;
    private String language;
    private BigDecimal price;
    private int quantity;
    private String condition;
    private String comment;

    public CardStock() {}

    public CardStock(Card card, User seller, String language, BigDecimal price, int quantity, String condition, String comment) {
        this.card = card;
        this.seller = seller;
        this.language = language;
        this.price = price;
        this.quantity = quantity;
        this.condition = condition;
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cardId")
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId")
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
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
