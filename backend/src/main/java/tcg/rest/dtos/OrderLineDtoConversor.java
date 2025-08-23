package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import tcg.model.entities.OrderLine;

public class OrderLineDtoConversor {

    private OrderLineDtoConversor(){}

    public static OrderLineDto toOrderLineDto(OrderLine orderLine) {
        return new OrderLineDto(
                orderLine.getId(),
                orderLine.getOrder() != null ? orderLine.getOrder().getId() : null,
                orderLine.getCard() != null ? orderLine.getCard().getName() : null,
                orderLine.getCard() != null ? orderLine.getCard().getId() : null,
                orderLine.getLanguage(),
                orderLine.getPrice(),
                orderLine.getQuantity(),
                orderLine.getCondition(),
                orderLine.getSeller().getId(),
                orderLine.getSeller().getUsername()
        );
    }

    public static List<OrderLineDto> toOrderLineDtos(List<OrderLine> orderLines) {
        return orderLines.stream()
                .map(OrderLineDtoConversor::toOrderLineDto)
                .collect(Collectors.toList());
    }
}
