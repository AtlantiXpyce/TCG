package tcg.model.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class CardStock {

    private Long id;
    private Card card;
    private User seller;
    private BigDecimal price;
    private int quantity;
    private int cartQuantity;
    private Condition condition;
    private String comment;
    private CardLanguage lang;

    public CardStock() {}

    public CardStock(Card card, User seller, BigDecimal price, int quantity, Condition condition, String comment, CardLanguage lang) {
        this.card = card;
        this.seller = seller;
        this.lang = lang;
        this.price = price;
        this.quantity = quantity;
        this.condition = condition;
        this.comment = comment;
    }
    
    public CardStock(Long id, Card card, User seller, BigDecimal price, int quantity, int cartQuantity,
            Condition condition, String comment, CardLanguage lang) {
        this.id = id;
        this.card = card;
        this.seller = seller;
        this.price = price;
        this.quantity = quantity;
        this.cartQuantity = cartQuantity;
        this.condition = condition;
        this.comment = comment;
        this.lang = lang;
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

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "conditionid")
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "lang")
    public CardLanguage getLang() {
        return lang;
    }

    public void setLang(CardLanguage lang) {
        this.lang = lang;
    }

}
