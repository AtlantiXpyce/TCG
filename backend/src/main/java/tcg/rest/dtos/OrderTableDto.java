package tcg.rest.dtos;

import java.time.LocalDateTime;

public class OrderTableDto {

    private Long id;
    private LocalDateTime orderDate;
    private Integer rating;
    private String review;
    private LocalDateTime arrivalDate;
    private Long buyerId;
    private String buyerName;
    private Long sellerId;
    private String sellerName;
    private Boolean isSent;

    public OrderTableDto() {}

    public OrderTableDto(Long id, LocalDateTime orderDate, Integer rating, String review, String buyerName,
                         LocalDateTime arrivalDate, Long buyerId, Boolean isSent, String sellerName, Long sellerId) {
        this.id = id;
        this.orderDate = orderDate;
        this.rating = rating;
        this.review = review;
        this.arrivalDate = arrivalDate;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.isSent = isSent;
        this.sellerName = sellerName;
        this.sellerId = sellerId;
    }

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

    public Long getBuyerId() {
        return buyerId;
    }
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Boolean getIsSent() {
        return isSent;
    }

    public void setIsSent(Boolean isSent) {
        this.isSent = isSent;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
