package tcg.rest.dtos;

public class CardDetailsDto {
    private Long id;
    private String name;
    private String cardNumber;
    private String artVariant;
    private String setName;
    private String rarityName;
    private String tcgName;
    private String imageBase64;

    public CardDetailsDto(Long id, String name, String cardNumber, String artVariant,
                          String setName, String rarityName, String tcgName, String imageBase64) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.artVariant = artVariant;
        this.setName = setName;
        this.rarityName = rarityName;
        this.tcgName = tcgName;
        this.imageBase64 = imageBase64;
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

    public String getImageBase64() {
        return imageBase64;
    }
}
