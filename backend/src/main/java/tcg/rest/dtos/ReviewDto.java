package tcg.rest.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewDto {

    @NotNull
    private Long orderTableId;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer rating;

    @Size(min = 0, max = 100)
    private String review;

    @NotNull
    private Long buyerId;

    public ReviewDto() {
    }

    public ReviewDto(Long orderTableId, Integer rating, String review, Long buyerId) {
        this.orderTableId = orderTableId;
        this.rating = rating;
        this.review = review;
        this.buyerId = buyerId;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(Long orderTableId) {
        this.orderTableId = orderTableId;
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

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "orderTableId=" + orderTableId +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", buyerId=" + buyerId +
                '}';
    }
}
