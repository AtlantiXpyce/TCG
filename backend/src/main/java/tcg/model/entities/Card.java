package tcg.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Card")
public class Card {
    
    private Long id;
	private String name;
    private Tcg tcg;
    private Set set;
    private Rarity rarity;
    private String cardNumber;
    private String artVariant;
    @Lob
    private byte[] imagen;
    
    public Card() {}

    public Card(String name, Tcg tcg, Set set, Rarity rarity, String cardNumber, String artVariant) {
        this.name = name;
        this.tcg = tcg;
        this.set = set;
        this.rarity = rarity;
        this.cardNumber = cardNumber;
        this.artVariant = artVariant;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tcgId")
    public Tcg getTcg() {
        return tcg;
    }

    public void setTcg(Tcg tcg) {
        this.tcg = tcg;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "setId")
    public Set getSet() {
        return set;
    }

    public void setSet(Set cardSet) {
        this.set = cardSet;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rarityId")
    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String number) {
        this.cardNumber = number;
    }

    public String getArtVariant() {
        return artVariant;
    }

    public void setArtVariant(String artVariant) {
        this.artVariant = artVariant;
    }

    public byte[] getImagen(){
        return imagen;
    }

    public void setImagen(byte[] imagen){
        this.imagen = imagen;
    }
}
