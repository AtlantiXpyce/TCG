package tcg.rest.dtos;

public class CardSearchDto {
    private Long id;
    private String name;
    private String cardNumber;
    private String artVariant;
    private String setName;
    private String rarityName;
    private String tcgName;

    public CardSearchDto(Long id, String name, String cardNumber, String artVariant,
                          String setName, String rarityName, String tcgName) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.artVariant = artVariant;
        this.setName = setName;
        this.rarityName = rarityName;
        this.tcgName = tcgName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getArtVariant() {
        return artVariant;
    }

    public String getSetName() {
        return setName;
    }

    public String getRarityName() {
        return rarityName;
    }

    public String getTcgName() {
        return tcgName;
    }
}
