package tcg.rest.controllers;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tcg.model.entities.Card;
import tcg.model.entities.CardSet;
import tcg.model.entities.Tcg;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.services.CardService;
import tcg.model.services.CardSetService;
import tcg.model.services.TcgService;
import tcg.rest.dtos.CardDetailsDto;
import tcg.rest.dtos.CardSetConversor;
import tcg.rest.dtos.CardSetDto;
import tcg.rest.dtos.TcgConversor;
import tcg.rest.dtos.TcgDto;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardSetService cardSetService;

    @Autowired
    private TcgService tcgService;

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<String> handleCardNotFound(CardNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDetailsDto> getCard(@PathVariable Long id) throws CardNotFoundException {
        Card card = cardService.getCardById(id);

        String imageBase64 = card.getImagen() != null
                ? Base64.getEncoder().encodeToString(card.getImagen())
                : null;

        CardDetailsDto dto;
        dto = new CardDetailsDto(
                card.getId(),
                card.getName(),
                card.getCardNumber(),
                card.getArtVariant(),
                card.getSet().getName(),
                card.getRarity().getName(),
                card.getTcg().getName(),
                imageBase64
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/sets")
    public List<CardSetDto> findAllCardSets(){
        List<CardSet> sets = cardSetService.getAllCardSets();
        return CardSetConversor.toCardSetDtos(sets);
    }

    @GetMapping("/tcgs")
    public List<TcgDto> findAllTcgs(){
        List<Tcg> tcgs = tcgService.getAllTcgs();
        return TcgConversor.toTcgDtos(tcgs);
    }
    
}
