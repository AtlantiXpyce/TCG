package tcg.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tcg.model.entities.Card;
import tcg.model.entities.CardSet;
import tcg.model.entities.Tcg;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.services.CardService;
import tcg.model.services.CardSetService;
import tcg.model.services.TcgService;
import tcg.rest.dtos.CardDetailsConversor;
import tcg.rest.dtos.CardDetailsDto;
import tcg.rest.dtos.CardSearchConversor;
import tcg.rest.dtos.CardSearchDto;
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
        
        return ResponseEntity.ok(CardDetailsConversor.toCardDetailsDto(card));
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

    @GetMapping("/search")
    public Page<CardSearchDto> searchCards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long setId,
            @RequestParam(required = false) Long tcgId,
            @RequestParam(required = false) Integer page) {
        
        Pageable pageable = createPageable(page);
        return CardSearchConversor.toCardSearchDtos(cardService.findCards(name, setId, tcgId, pageable));
    }

    public Pageable createPageable(Integer page) {
        if(page == null || page < 0){
            page = 0;
        }
        return PageRequest.of(page, 10);
    }
}
