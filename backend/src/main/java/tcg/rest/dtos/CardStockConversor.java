package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import tcg.model.entities.CardStock;

public class CardStockConversor {

    private CardStockConversor() {}

    public static CardStockDto toCardStockDto(CardStock stock) {
        if (stock == null) return null;

        return new CardStockDto(
            stock.getId(),
            stock.getCard() != null ? stock.getCard().getId() : null,
            stock.getSeller() != null ? stock.getSeller().getId() : null,
            stock.getLang() != null ? stock.getLang().getName() : null,
            stock.getPrice(),
            stock.getQuantity(),
            stock.getCondition() != null ? stock.getCondition().getName() : null,
            stock.getComment()
        );
    }

    public static List<CardStockDto> toCardStockDtos(List<CardStock> stocks) {
        return stocks.stream()
                     .map(CardStockConversor::toCardStockDto)
                     .collect(Collectors.toList());
    }

    public static Page<CardStockDto> toCardStockDtos(Page<CardStock> stockPage) {
        return stockPage.map(CardStockConversor::toCardStockDto);
    }
}
