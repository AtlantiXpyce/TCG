package tcg.model.entities;

import jakarta.persistence.*;

@Entity
public class Cart {

    private Long id;
    private CardStock cardStock;
    private int quantity;
    private User user;

    public Cart() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cardStockId")
    public CardStock getCardStock() {
        return cardStock;
    }

    public void setCardStock(CardStock cardStock) {
        this.cardStock = cardStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
