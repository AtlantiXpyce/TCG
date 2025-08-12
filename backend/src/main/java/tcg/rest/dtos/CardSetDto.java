package tcg.rest.dtos;

public class CardSetDto {
    private Long id;
	private String name;
    private String tcg;

    public CardSetDto() {}

    public CardSetDto(Long id, String name, String tcg){
        this.id = id;
        this.name = name;
        this.tcg = tcg;
    }
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTcg(){
        return tcg;
    }

    public void setTcg(String tcg){
        this.tcg = tcg;
    }
}
