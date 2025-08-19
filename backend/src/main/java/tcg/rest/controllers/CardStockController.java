package tcg.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import tcg.model.entities.CardLanguage;
import tcg.model.entities.CardStock;
import tcg.model.entities.Condition;
import tcg.model.exceptions.CardNotFoundException;
import tcg.model.exceptions.UserNotFoundException;
import tcg.model.services.CardLanguageService;
import tcg.model.services.CardStockService;
import tcg.model.services.ConditionService;
import tcg.rest.common.JwtGenerator;
import tcg.rest.dtos.CardStockDto;
import tcg.rest.dtos.ConditionConversor;
import tcg.rest.dtos.ConditionDto;
import tcg.rest.dtos.LanguageConversor;
import tcg.rest.dtos.LanguageDto;


@RestController
@RequestMapping("/cardStock")
public class CardStockController {
    
    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private CardStockService cardStockService;

    @Autowired
    private CardLanguageService cardLanguageService;

    @Autowired
    private ConditionService conditionService;

    @PostMapping("/add")
    public ResponseEntity<?> sellCard(@RequestBody CardStockDto dto, HttpServletRequest request) throws CardNotFoundException, UserNotFoundException{

        String token = request.getHeader("Authorization").substring(7);
        Long authId = jwtGenerator.getIdFromToken(token);

        CardStock cardStock = cardStockService.createStock(dto.getCardId(), authId, dto.getLanguageId(), dto.getPrice(), dto.getQuantity(), dto.getConditionId(), dto.getComment());

        return ResponseEntity.ok(new CardStockDto());
    }

    @GetMapping("/languages")
    public List<LanguageDto> getAllLanguages() {
        List<CardLanguage> languages = cardLanguageService.getAllLanguages();
        return LanguageConversor.toLanguageDtos(languages);
    }

    @GetMapping("/conditions")
    public List<ConditionDto> getAllConditions() {
        List<Condition> conditions = conditionService.getAllConditions();
        return ConditionConversor.toConditionDtos(conditions);
    }

}