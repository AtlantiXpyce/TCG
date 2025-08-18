package tcg.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import tcg.model.entities.CardStock;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.CardStockService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.CardStockDto;


@RestController
@RequestMapping("/cardStock")
public class CardStockController {
    
    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private CardStockService cardStockService;

    @PostMapping("/add")
    public ResponseEntity<?> getCard(@RequestBody CardStockDto dto, HttpServletRequest request) throws CardNotFoundException, UserNotFoundException{

        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        if (!authId.equals(dto.getSellerId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario no autorizado");
        }

        CardStock cardStock = cardStockService.createStock(dto.getCardId(), dto.getSellerId(), dto.getLanguageId(), dto.getPrice(), dto.getQuantity(), dto.getConditionId(), dto.getComment());

        return ResponseEntity.ok(new CardStockDto());
    }
}