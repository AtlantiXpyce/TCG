package tcg.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tcg.model.services.CardStockService;
import tcg.model.services.UserService;


@RestController
@RequestMapping("/cardStock")
public class CardStockController {
    @Autowired
    private UserService userService;

    @Autowired
    private CardStockService cardStockService;
}