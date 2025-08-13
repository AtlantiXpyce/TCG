package tcg.rest.dtos;

import org.springframework.data.domain.Page;

import tcg.model.entities.Card;

public class CardSearchConversor {
    
    private CardSearchConversor(){}

    public static final CardSearchDto toCardSearchDto(Card card) {               
        return new CardSearchDto(card.getId(),
                card.getName(),
                card.getCardNumber(),
                card.getArtVariant(),
                card.getSet().getName(),
                card.getRarity().getName(),
                card.getTcg().getName());
    }

    public static Page<CardSearchDto> toCardSearchDtos(Page<Card> cards) {
    return cards.map(c -> toCardSearchDto(c));
}
}
