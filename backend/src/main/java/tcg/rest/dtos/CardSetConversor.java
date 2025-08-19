package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import tcg.model.entities.CardSet;

public class CardSetConversor {
    
    private CardSetConversor(){}

    public static final CardSetDto toCardSetDto(CardSet cardSet) {
        return new CardSetDto(cardSet.getId(),cardSet.getName(),cardSet.getTcg().getName());
    }

    public static final List<CardSetDto> toCardSetDtos(List<CardSet> cardSets){
        return cardSets.stream().map(c -> toCardSetDto(c)).collect(Collectors.toList());
    }
}
