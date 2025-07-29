package tcg.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Set {
    private Long id;
	private String name;
    private Tcg tcg;
    
    public Set() {}

    public Set(String name, Tcg tcg) {
        this.name = name;
        this.tcg = tcg;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
}
