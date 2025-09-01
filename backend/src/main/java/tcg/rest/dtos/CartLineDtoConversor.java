package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import tcg.model.entities.Card;
import tcg.model.entities.CardSet;
import tcg.model.entities.CardStock;
import tcg.model.entities.Cart;
import tcg.model.entities.Tcg;
import tcg.model.entities.User;

public class CartLineDtoConversor {

    private CartLineDtoConversor() {}
    
    public static CartLineDto toCartLineDto(Cart cart) {
        CardStock cs = cart.getCardStock();
        Card card = cs.getCard();
        CardSet set = card.getSet();
        Tcg tcg = set.getTcg();
        User seller = cs.getSeller();

        return new CartLineDto(
            cart.getId(),
            cs.getId(),
            card.getName(),
            set.getName(),
            tcg.getName(),
            cs.getPrice(),
            cart.getQuantity(),
            cs.getCondition().getName(),
            cs.getLang().getName(),
            seller.getId(),
            seller.getUsername()
        );
    }

    public static List<CartLineDto> toCartLineDtos(List<Cart> carts) {
    return carts.stream()
                .map(CartLineDtoConversor::toCartLineDto)
                .collect(Collectors.toList());
}
}
