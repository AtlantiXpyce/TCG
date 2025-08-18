package tcg.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "condition")
public class Condition {
    private Long id;
	private String name;
    private String initial;
    
    public Condition() {}

    public Condition(String name) {
        this.name = name;
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

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
		this.initial = initial;
	}
}

