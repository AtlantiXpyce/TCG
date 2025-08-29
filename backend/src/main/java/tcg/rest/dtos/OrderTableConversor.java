package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import tcg.model.entities.OrderTable;

public class OrderTableConversor {

    private OrderTableConversor() {}

    public static OrderTableDto toOrderTableDto(OrderTable order) {
        if (order == null) return null;

        return new OrderTableDto(
            order.getId(),
            order.getOrderDate(),
            order.getRating(),
            order.getReview(),
            order.getBuyer() != null ? order.getBuyer().getUsername() : null,
            order.getArrivalDate(),
            order.getBuyer() != null ? order.getBuyer().getId() : null,
            order.getIsSent(),
            order.getSeller() != null ? order.getSeller().getUsername() : null,
            order.getSeller() != null ? order.getSeller().getId() : null
        );
    }

    public static List<OrderTableDto> toOrderTableDtos(List<OrderTable> orders) {
        return orders.stream()
                     .map(OrderTableConversor::toOrderTableDto)
                     .collect(Collectors.toList());
    }

    public static Page<OrderTableDto> toOrderTableDtos(Page<OrderTable> orderPage) {
        return orderPage.map(OrderTableConversor::toOrderTableDto);
    }
}
