package tcg.rest.dtos;

import java.util.Base64;

import org.springframework.data.domain.Page;

import tcg.model.entities.Card;

public class CardDetailsConversor {
    
    private CardDetailsConversor(){}

    public static final CardDetailsDto toCardDetailsDto(Card card) {
        String imageBase64 = card.getImagen() != null
                ? Base64.getEncoder().encodeToString(card.getImagen())
                : null;
               
        return new CardDetailsDto(card.getId(),
                card.getName(),
                card.getCardNumber(),
                card.getArtVariant(),
                card.getSet().getName(),
                card.getRarity().getName(),
                card.getTcg().getName(),
                imageBase64);
    }

    public static Page<CardDetailsDto> toCardDetailDtos(Page<Card> cards) {
    return cards.map(c -> toCardDetailsDto(c));
}
}
