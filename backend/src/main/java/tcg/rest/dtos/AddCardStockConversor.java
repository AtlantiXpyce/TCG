package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import tcg.model.entities.CardStock;

public class AddCardStockConversor {

    private AddCardStockConversor() {}

    public static AddCardStockDto toAddCardStockDto(CardStock stock) {
        return new AddCardStockDto(
            stock.getCard() != null ? stock.getCard().getId() : null,
            stock.getLang() != null ? stock.getLang().getId() : null,
            stock.getPrice(),
            stock.getQuantity(),
            stock.getCondition() != null ? stock.getCondition().getId() : null,
            stock.getComment()
        );
    }

    public static List<AddCardStockDto> toAddCardStockDtos(List<CardStock> stocks) {
        return stocks.stream().map(AddCardStockConversor::toAddCardStockDto).collect(Collectors.toList());
    }

    public static Page<AddCardStockDto> toAddCardStockDtos(Page<CardStock> stockPage) {
        return stockPage.map(AddCardStockConversor::toAddCardStockDto);
    }
}
