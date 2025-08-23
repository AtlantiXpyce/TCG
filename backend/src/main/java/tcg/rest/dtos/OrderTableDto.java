package tcg.rest.dtos;

import java.time.LocalDateTime;

public class OrderTableDto {

    private Long id;
    private LocalDateTime orderDate;
    private Integer rating;
    private String review;
    private LocalDateTime arrivalDate;
    private Long buyerId;

    public OrderTableDto() {}

    public OrderTableDto(Long id, LocalDateTime orderDate, Integer rating,
                         String review, LocalDateTime arrivalDate, Long buyerId) {
        this.id = id;
        this.orderDate = orderDate;
        this.rating = rating;
        this.review = review;
        this.arrivalDate = arrivalDate;
        this.buyerId = buyerId;
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
}
