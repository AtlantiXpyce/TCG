package tcg.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderTable {

    private Long id;
    private LocalDateTime orderDate;
    private Integer rating;
    private String review;
    private LocalDateTime arrivalDate;
    private User buyer;
    private User seller;
    private Boolean isSent;

    public OrderTable() {}

    public OrderTable(LocalDateTime orderDate, User buyer, User seller) {
        this.orderDate = orderDate;
        this.buyer = buyer;
        this.seller = seller;
        this.isSent = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "buyerid")
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Boolean getIsSent() {
        return isSent;
    }

    public void setIsSent(Boolean isSent) {
        this.isSent = isSent;
    }

    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "sellerid")
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
